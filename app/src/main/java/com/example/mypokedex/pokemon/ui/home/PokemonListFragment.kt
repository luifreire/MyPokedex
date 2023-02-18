package com.example.mypokedex.pokemon.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypokedex.databinding.FragmentPkmnListBinding
import com.example.mypokedex.pokemon.api.PokemonDataSourceImpl
import com.example.mypokedex.pokemon.data.PokemonRepositoryImpl

class PokemonListFragment: Fragment() {
    private lateinit var binding: FragmentPkmnListBinding
    private val pokemonClickCallBack = { species: String ->
        val directions = PokemonListFragmentDirections.navigateToPokemonDetail(species)
        findNavController().navigate(directions)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPkmnListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        PokemonRepositoryImpl(PokemonDataSourceImpl()).getListOfPokemons {
            it?.let { it1 ->
                val adapter = PokedexListAdapter(it1)
                adapter.onItemClick = pokemonClickCallBack
                binding.rvPokedexList.layoutManager = LinearLayoutManager(this.context)
                binding.rvPokedexList.adapter = adapter
            }
        }
    }
}