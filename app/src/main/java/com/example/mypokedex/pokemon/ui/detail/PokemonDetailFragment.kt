package com.example.mypokedex.pokemon.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mypokedex.databinding.FragmentDetailUiBinding
import com.example.mypokedex.pokemon.api.PokemonDataSourceImpl
import com.example.mypokedex.pokemon.data.PokemonRepositoryImpl
import org.w3c.dom.Text
import java.util.*

class PokemonDetailFragment: Fragment(), TextToSpeech.OnInitListener {
    private lateinit var binding: FragmentDetailUiBinding
    val args by navArgs<PokemonDetailFragmentArgs>()
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
        PokemonRepositoryImpl(PokemonDataSourceImpl()).getPokemon(args.pokemonSpecies) {
            it?.let {pokemon ->
                println(pokemon)
                binding.tvPokemonName.text = pokemon.name.capitalize()
                binding.tvHeight.text = "${(pokemon.height / 10).toString()} m"
                binding.tvWeight.text = "${(pokemon.weight / 10).toString()} kg"
                binding.tvAbilities.text = pokemon.abilities.map { ability -> ability.capitalize()  }.joinToString(", ")
                binding.tvEggGroup.text = pokemon.eggGroup.map { eggGroup -> eggGroup.capitalize() }.joinToString(", ")
                binding.tvCatchRate.text = pokemon.catchRate.toString()

                var femaleRatio = pokemon.genderRatio / 8
                var maleRatio = 1 - femaleRatio
                var formattedFemaleRatio = femaleRatio * 100
                var formattedMaleRatio = maleRatio * 100
                binding.tvGender.text = "${formattedFemaleRatio.toString()}%"
                binding.tvGender2.text = "${formattedMaleRatio.toString()}%"
                binding.tvSpecies.text = pokemon.species.capitalize()
                binding.tvDexQuote.text = pokemon.quote.replace(Regex("\n"), " ")
                val acceptedColors = listOf( "red", "blue", "green", "black", "white", "gray", "cyan", "magenta", "yellow", "lightgray", "darkgray", "grey", "lightgrey", "darkgrey", "aqua", "fuchsia", "lime", "maroon", "navy", "olive", "purple", "silver","teal")
                var bgColor: Int = 0
                if (acceptedColors.contains(pokemon.bgColor)) {
                    bgColor = Color.parseColor(pokemon.bgColor)
                } else {
                    bgColor = Color.parseColor("black")
                }

                var newColor = Color.HSVToColor(FloatArray(3).apply {
                    Color.colorToHSV(bgColor, this)
                    this[1] *= 0.35f
//                    this[2] *= 1.1f
                })
                binding.llImageBackground.setBackgroundColor(newColor)
                if (pokemon.imageURL.isNotEmpty()) {
                    Glide.with(this).load(pokemon.imageURL).into(binding.ivPokemonImage)
                }
                tts = TextToSpeech(this.context,this)
            }
        }
    }

    override fun onInit(p0: Int) {
        if (p0 == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language not supported!")
            } else {
               tts?.setSpeechRate(0.8f)
               tts?.speak("${binding.tvSpecies.text}", TextToSpeech.QUEUE_FLUSH, null, "")
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