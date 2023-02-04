package com.example.mypokedex.pokemon.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        PokemonRepositoryImpl(PokemonDataSourceImpl()).getPokemon("bulbassaur") {
            it?.let {pokemon ->
            binding.tvPokemonName.text = pokemon.name.capitalize()
            binding.tvHeight.text = pokemon.height.toString()
            binding.tvWeight.text = pokemon.weight.toString()
            binding.tvAbilities.text = pokemon.abilities.map { ability -> ability.capitalize()  }.joinToString(", ")
            binding.tvEggGroup.text = pokemon.eggGroup.map { eggGroup -> eggGroup.capitalize() }.joinToString(", ")
            binding.tvCatchRate.text = pokemon.catchRate.toString()

            var femaleRatio = pokemon.genderRatio / 8
            var maleRatio = 1 - femaleRatio
            var formattedFemaleRatio = femaleRatio * 100
            var formattedMaleRatio = maleRatio * 100
            binding.tvGender.text = formattedFemaleRatio.toString()+"%"
            binding.tvGender2.text = formattedMaleRatio.toString()+"%"
            }
        }
    }
}