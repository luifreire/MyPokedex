package com.example.mypokedex.pokemon.data

import kotlinx.coroutines.flow.Flow
import com.example.mypokedex.pokemon.data.model.Result
interface PokemonRepository {
    suspend fun getPokemon(name: String, completion: suspend (pokemon: Flow<Result<Pokemon?>>) -> Unit)
    suspend fun getListOfPokemons(callback: suspend (Flow<Result<List<String>>?>) -> Unit)
}


