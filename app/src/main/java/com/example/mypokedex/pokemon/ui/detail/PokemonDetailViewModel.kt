package com.example.mypokedex.pokemon.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.pokemon.api.PokemonDataSourceImpl
import com.example.mypokedex.pokemon.data.Pokemon
import com.example.mypokedex.pokemon.data.PokemonRepositoryImpl
import com.example.mypokedex.pokemon.data.model.Result
import kotlinx.coroutines.launch

class PokemonDetailViewModel(): ViewModel() {
    private val _pokemon = MutableLiveData<Result<Pokemon>>()
    val pokemon = _pokemon

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

    fun fetchPokemon(name: String) {
        viewModelScope.launch {
            PokemonRepositoryImpl(PokemonDataSourceImpl()).getPokemon(name) {
                it.collect {
                    _pokemon.value = it
                }
            }
        }
    }

    fun checkWeaknesses(pokemon: Pokemon): List<String>? {
        val pokeType1 = pokemon.types[0].type.name
        val typeOneWeaknesses = typesToWeaknesses[pokeType1.capitalize()]
        if (pokemon.types.size > 1) {
            val pokeType2 = pokemon.types[1].type.name
            val typeTwoWeaknesses = typesToWeaknesses[pokeType2.capitalize()]
            if(typeOneWeaknesses != null && typeTwoWeaknesses != null) {
                var joinedTypes: List<String> = typeOneWeaknesses.plus(typeTwoWeaknesses)
                return joinedTypes.mapNotNull { weakness -> if (weakness != pokeType2.capitalize()) weakness else null}
            } else {
                return null
            }
        }
        return typeOneWeaknesses
    }
}