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
import com.example.mypokedex.pokemon.data.Pokemon
import com.example.mypokedex.pokemon.data.PokemonRepositoryImpl

class PokemonDetailFragment: Fragment() {
    private lateinit var binding: FragmentDetailUiBinding
    val typesToWeaknesses = mapOf(
        "Normal" to listOf("Fighting"),
        "Fighting" to  listOf("Flying", "Psychic", "Fairy"),
        "Flying" to listOf("Rock", "Electric", "Ice"),
        "Poison" to listOf("Ground", "Psychic"),
        "Ground" to  listOf("Water", "Grass", "Ice"),
        "Rock" to listOf("Fighting", "Ground", "Steel", "Water", "Grass"),
        "Bug" to listOf("Flying", "Rock", "Fire"),
        "Ghost" to listOf("Ghost, Dark"),
        "Steel" to listOf("Fighting", "Ground", "Fire"),
        "Fire" to listOf("Ground", "Rock", "Water"),
        "Water" to listOf("Grass", "Electric"),
        "Grass" to listOf("Flying", "Poison", "Ghost", "Fire", "Ice"),
        "Electric" to listOf("Ground"),
        "Psychic" to listOf("Bug", "Ghost", "DarK"),
        "Ice" to listOf("Fighting", "Rock", "Steel", "Fire"),
        "Dragon" to listOf("Ice", "Dragon", "Fairy"),
        "Dark" to listOf("Fighting", "Bug", "Fairy"),
        "Fairy" to listOf("Poison", "Steel")
    )

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
                var pokeBackgroundColor = 0
                var acceptedColors = listOf<String>("black", "blue", "cyan", "dark gray", "gray", "green", "light gray", "magenta", "red", "transparent", "white", "yellow")
                    if (!acceptedColors.contains(pokemon.color)) {
                        pokeBackgroundColor = Color.BLACK
                    } else {
                        pokeBackgroundColor = Color.parseColor(pokemon.color)
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
                checkWeaknesses(pokemon)?.let {
                    binding.tvWeaknesses.text = it.joinToString(", ")
                }
            }
        }
    }
    private fun checkWeaknesses(pokemon: Pokemon): List<String>? {
        val pokeType1 = pokemon.types[0].type.name
        val typeOneWeaknesses = typesToWeaknesses[pokeType1]
        if (pokemon.types.size > 1) {
            val pokeType2 = pokemon.types[1].type.name
            val typeTwoWeaknesses = typesToWeaknesses[pokeType2]
            if(typeOneWeaknesses != null && typeTwoWeaknesses != null) {
                var joinedTypes: List<String> = typeOneWeaknesses.plus(typeTwoWeaknesses)
                return joinedTypes.mapNotNull { weakness -> if (weakness == pokeType2) weakness else null}
            } else {
                return null
            }
        }
        return typeOneWeaknesses
    }
}