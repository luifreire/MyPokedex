package com.example.mypokedex.pokemon.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypokedex.databinding.FragmentPkmnListBinding
import com.example.mypokedex.pokemon.api.PokemonDataSourceImpl
import com.example.mypokedex.pokemon.data.PokemonRepositoryImpl

class PokemonListFragment: Fragment() {
    lateinit var binding: FragmentPkmnListBinding

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
            binding.rvPokedexList.adapter = it?.let { it1 -> PokedexListAdapter(it1) }
            binding.rvPokedexList.layoutManager = LinearLayoutManager(this.context)
        }
    }
}