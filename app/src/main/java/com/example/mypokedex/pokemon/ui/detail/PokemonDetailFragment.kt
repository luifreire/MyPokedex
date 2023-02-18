package com.example.mypokedex.pokemon.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mypokedex.databinding.FragmentDetailUiBinding
import com.example.mypokedex.pokemon.data.Pokemon
import java.util.*
import com.example.mypokedex.pokemon.data.model.Result
import androidx.lifecycle.Observer
class PokemonDetailFragment: Fragment(), TextToSpeech.OnInitListener {
    private lateinit var binding: FragmentDetailUiBinding
    val args by navArgs<PokemonDetailFragmentArgs>()
    private val viewModel by viewModels<PokemonDetailViewModel>()
    private var tts: TextToSpeech? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailUiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribeUI()
    }

    fun subscribeUI() {
        viewModel.fetchPokemon(args.pokemonSpecies)
        viewModel.pokemon.observe(this.viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let {
                        tts = TextToSpeech(binding.root.context, this@PokemonDetailFragment)
                        var femaleRatio = it.genderRatio / 8
                        var maleRatio = 1 - femaleRatio
                        var formattedFemaleRatio = femaleRatio * 100
                        var formattedMaleRatio = maleRatio * 100

                        binding.tvPokemonName.text = it.name.capitalize()
                        var pokeBackgroundColor = 0
                        var acceptedColors = listOf<String>(
                            "black",
                            "blue",
                            "cyan",
                            "dark gray",
                            "gray",
                            "green",
                            "light gray",
                            "magenta",
                            "red",
                            "transparent",
                            "white",
                            "yellow"
                        )
                        if (!acceptedColors.contains(it.color)) {
                            pokeBackgroundColor = Color.BLACK
                        } else {
                            pokeBackgroundColor = Color.parseColor(it.color)
                        }

                        var newColor = Color.HSVToColor(FloatArray(3).apply {
                            Color.colorToHSV(pokeBackgroundColor, this)
                            this[1] = this[1] * 0.35f
                        })
                        binding.llImageBackground.setBackgroundColor(newColor)
                        val pokeImageUrl = it.imageUrl
                        if (pokeImageUrl.isNotEmpty()) {
                            Glide.with(this@PokemonDetailFragment).load(it.imageUrl)
                                .into(binding.ivPokemonImage)
                        }

                        binding.tvDexQuote.text = it.quote.replace("\n", " ")
                        binding.tvSpecies.text = it.species.capitalize()
                        binding.tvHeight.text = "${(it.height / 10).toString()} m"
                        binding.tvWeight.text = "${(it.weight / 10).toString()} kg"
                        binding.tvAbilities.text =
                            it.abilities.map { ability -> ability.capitalize() }.joinToString(", ")
                        binding.tvEggGroup.text =
                            it.eggGroup.map { eggGroup -> eggGroup.capitalize() }.joinToString(", ")
                        binding.tvCatchRate.text = it.catchRate.toString()
                        binding.tvGender.text = "${formattedFemaleRatio.toString()}%"
                        binding.tvGender2.text = "${formattedMaleRatio.toString()}%"
                        viewModel.checkWeaknesses(it)?.let {
                            binding.tvWeaknesses.text = it.joinToString(", ")
                        }
                    }
                }
                Result.Status.ERROR -> {
                    println("Error getting detail...")
                }
                Result.Status.LOADING -> {
                    println("Loading detail...")
                }
            }
        })
    }

    override fun onInit(p0: Int) {
        if (p0 == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language not supported!")
            } else {
               tts?.setSpeechRate(0.8f)
               tts?.speak("${binding.tvSpecies.text}", TextToSpeech.QUEUE_FLUSH, null, "")
                tts?.playSilentUtterance(300, TextToSpeech.QUEUE_ADD, null)
                tts?.speak("${binding.tvEggGroup.text}", TextToSpeech.QUEUE_ADD, null, "")
                tts?.playSilentUtterance(600, TextToSpeech.QUEUE_ADD, null)
                tts?.speak("${binding.tvDexQuote.text}", TextToSpeech.QUEUE_ADD, null, "")

            }
        }
    }

    override fun onDestroy() {
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        super.onDestroy()
    }
}