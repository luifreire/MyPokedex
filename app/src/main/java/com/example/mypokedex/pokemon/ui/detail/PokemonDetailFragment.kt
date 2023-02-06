package com.example.mypokedex.pokemon.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.mypokedex.databinding.FragmentDetailUiBinding
import com.example.mypokedex.pokemon.api.PokemonDataSourceImpl
import com.example.mypokedex.pokemon.data.PokemonRepositoryImpl

class PokemonDetailFragment: Fragment() {
    private lateinit var binding: FragmentDetailUiBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailUiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        PokemonRepositoryImpl(PokemonDataSourceImpl()).getPokemon("bulbasaur") {
            it?.let {pokemon ->
                var femaleRatio = pokemon.genderRatio / 8
                var maleRatio = 1 - femaleRatio
                var formattedFemaleRatio = femaleRatio * 100
                var formattedMaleRatio = maleRatio * 100

                binding.tvPokemonName.text = pokemon.name.capitalize()
                var pokeBackgroundColor = Color.parseColor(pokemon.color)
                var acceptedColors = listOf<String>("BLACK", "BLUE", "CYAN", "DARK GRAY", "GRAY GREEN", "LIGHT GRAY", "MAGENTA", "RED", "TRANSPARENT", "WHITE", "YELLOW")
                    if (!acceptedColors.contains(pokemon.color)) {
                        pokeBackgroundColor = Color.BLACK
                    }
                var newColor = Color.HSVToColor(FloatArray(3).apply {
                    Color.colorToHSV(pokeBackgroundColor, this)
                    this[1] = this[1] * 0.35f
                })
                binding.llImageBackground.setBackgroundColor(newColor)
                val pokeImageUrl = pokemon.imageUrl
                if (pokeImageUrl.isNotEmpty()) {
                    Glide.with(this).load(pokemon.imageUrl).into(binding.ivPokemonImage)
                }
                binding.tvDexQuote.text = pokemon.quote.replace("\n"," ")
                binding.tvSpecies.text = pokemon.species.capitalize()
                binding.tvHeight.text = "${(pokemon.height / 10).toString()} m"
                binding.tvWeight.text = "${(pokemon.weight / 10).toString()} kg"
                binding.tvAbilities.text = pokemon.abilities.map { ability -> ability.capitalize()  }.joinToString(", ")
                binding.tvEggGroup.text = pokemon.eggGroup.map { eggGroup -> eggGroup.capitalize() }.joinToString(", ")
                binding.tvCatchRate.text = pokemon.catchRate.toString()
                binding.tvGender.text = "${formattedFemaleRatio.toString()}%"
                binding.tvGender2.text = "${formattedMaleRatio.toString()}%"
            }
        }
    }
}