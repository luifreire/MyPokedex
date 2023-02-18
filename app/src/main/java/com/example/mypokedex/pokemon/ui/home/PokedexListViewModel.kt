package com.example.mypokedex.pokemon.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.pokemon.api.PokemonDataSourceImpl
import com.example.mypokedex.pokemon.data.PokemonRepositoryImpl
import com.example.mypokedex.pokemon.data.model.Result
import kotlinx.coroutines.launch

class PokedexListViewModel: ViewModel() {
    private val _nameList = MutableLiveData<Result<List<String>>>()
    val names = _nameList

    init {
        fetchNames()
    }

    private fun fetchNames() {
        viewModelScope.launch {
            PokemonRepositoryImpl(PokemonDataSourceImpl()).getListOfPokemons {
                it.collect {
                    _nameList.value = it

                }
            }
        }
    }
}