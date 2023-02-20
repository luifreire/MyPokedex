package com.example.mypokedex.pokemon.ui.camera

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.*
import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mypokedex.R
import com.example.mypokedex.databinding.FragmentCameraBinding
import com.example.mypokedex.ml.PkmnSimpleModel
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.model.Model
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PokedexCameraFragment: Fragment() {
    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private val recogViewModel: RecognitionViewModel by viewModels()

    private lateinit var safeContext: Context
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var binding: FragmentCameraBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
    }

    private fun getStatusBarHeight(): Int {
        val resourceId = safeContext.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            safeContext.resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
        cameraExecutor = Executors.newSingleThreadExecutor()
        recogViewModel.recognitionList.observe(this.viewLifecycleOwner, Observer {
            Log.d("pokedexRecognitionViewModel", "Collecting items ${it.toString()}")
            if (it[0].confidence > 0.3) {
                val mostLikelyPokemon = it[0].label
                Log.d("pokedexNavigationSetup", "Requesting Detail Screen with $mostLikelyPokemon")
                val directions = PokedexCameraFragmentDirections.interpretPokemon(mostLikelyPokemon)
                findNavController().navigate(directions)
            }
        })
    }

    private fun startCamera() {
        var cameraController = LifecycleCameraController(safeContext)
        val cameraProviderFuture = ProcessCameraProvider.getInstance(safeContext)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            }
            imageCapture = ImageCapture.Builder().build()
            imageAnalyzer = ImageAnalysis.Builder()
                .setTargetResolution(Size(224, 224))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also { analysisUseCase ->
                    analysisUseCase.setAnalyzer(cameraExecutor, ImageAnalyzer(this.safeContext) { items ->
                        Log.d("pokedex", items.toString())
                        recogViewModel.updateData(items)
                    })
                }
            val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

            try {
                cameraProvider.unbindAll()

                camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalyzer)
//                cameraController.bindToLifecycle(this.viewLifecycleOwner, cameraSelector, preview, imageAnalyzer)
//                binding.viewFinder.controller = cameraController
                preview?.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            } catch (e: Exception) {
                Log.e("Camera Fragment", "Use case binding failed", e)
            }

        }, ContextCompat.getMainExecutor(safeContext))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(safeContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onPause() {
        super.onPause()
        isOffline = true
    }

    override fun onResume() {
        super.onResume()
        isOffline = false
    }

    @Deprecated("Deprecated in Java", replaceWith = ReplaceWith(" registerForActivityResult(ActivityResultContract, ActivityResultCallback)") )
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if(allPermissionsGranted()) {
                 startCamera()
            } else {
                Toast.makeText(safeContext, "Permissons not granted by the user", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        internal const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        var isOffline = false
    }

    private class CornerAnalyzer(private val listener: () -> Unit) : ImageAnalysis.Analyzer {

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }

        @SuppressLint("UnsafeExperimentalUsageError")
        override fun analyze(imageProxy: ImageProxy) {
            if (!isOffline) {
                listener()
            }
            imageProxy.close() // important! if it is not closed it will only run once
        }

    }
}

private class ImageAnalyzer(ctx: Context, private val listener: (recognition: List<Recognition>) -> Unit): ImageAnalysis.Analyzer {
    private val pokedexModel: PkmnSimpleModel by lazy {
        val compatList = CompatibilityList()
        val options = if (compatList.isDelegateSupportedOnThisDevice) {
            Log.d("pokedex", "This device is GPU Compatible ")
            Model.Options.Builder().setDevice(Model.Device.GPU).build()
        } else {
            Log.d("pokedex", "This device is GPU Incompatible ")
            Model.Options.Builder().setNumThreads(4).build()
        }
        PkmnSimpleModel.newInstance(ctx, options)
    }
    override fun analyze(image: ImageProxy) {
        val items = mutableListOf<Recognition>()
        val tfImage = TensorImage.fromBitmap(image.toBitmap())

        val outputs = pokedexModel.process(tfImage)
            .probabilityAsCategoryList.apply {
                sortByDescending { it.score }
            }.take(MAX_RESULT_DISPLAY)

        for (output in outputs) {
            items.add(Recognition(output.label, output.score))
        }

        listener(items.toList())

        image.close()
    }
}

private const val MAX_RESULT_DISPLAY = 3

fun ImageProxy.toBitmap(): Bitmap {
    val yBuffer = planes[0].buffer
    val uBuffer = planes[1].buffer
    val vBuffer = planes[2].buffer

    val ySize = yBuffer.remaining()
    val uSize = uBuffer.remaining()
    val vSize = vBuffer.remaining()

    val nv21 = ByteArray(ySize + uSize + vSize)

    yBuffer.get(nv21, 0, ySize)
    vBuffer.get(nv21, ySize, vSize)
    uBuffer.get(nv21, ySize + vSize, uSize)

    val yuvImage = YuvImage(nv21, ImageFormat.NV21, this.width, this.height, null)
    val out = ByteArrayOutputStream()
    yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 100, out)
    val imageBytes = out.toByteArray()

    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

