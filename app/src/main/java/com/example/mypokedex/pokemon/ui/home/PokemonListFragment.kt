package com.example.mypokedex.pokemon.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypokedex.databinding.FragmentPkmnListBinding
import com.example.mypokedex.pokemon.api.PokemonDataSourceImpl
import com.example.mypokedex.pokemon.data.PokemonRepositoryImpl
import com.example.mypokedex.pokemon.data.model.Result
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
class PokemonListFragment: Fragment() {
    private lateinit var binding: FragmentPkmnListBinding
    private var list = ArrayList<String>()
    private val viewModel by viewModels<PokedexListViewModel>()
    private lateinit var pokedexAdapter: PokedexListAdapter

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
        pokedexAdapter = PokedexListAdapter(list)
        val adapter = pokedexAdapter
        adapter.onItemClick = pokemonClickCallBack
        binding.rvPokedexList.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rvPokedexList.adapter = adapter
        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.names.observe(this, Observer { result ->
            when(result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let {
                        pokedexAdapter.updateData(it)
                    }
                }
                Result.Status.LOADING -> {
                    println("Loading list...")
                }
                Result.Status.ERROR -> {
                    println("There was an error")
                }
            }
        })
    }
}