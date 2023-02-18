package com.example.mypokedex.pokemon.api

import com.example.mypokedex.pokemon.data.model.PokemonDetailResponse
import com.example.mypokedex.pokemon.data.model.PokemonSpeciesAPIResponse
import com.example.mypokedex.pokemon.data.model.PokemonListResponse
import com.example.mypokedex.pokemon.data.model.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI {
    @GET("pokemon-species/{species}")
    suspend fun species(@Path("species") specie: String): Response<PokemonSpeciesAPIResponse>
    @GET("pokemon/{name}")
    suspend fun pokemon(@Path("name") name: String): Response<PokemonDetailResponse>
    @GET("pokemon")
    suspend fun pokemonList(): Response<PokemonListResponse>
}

interface PokemonDataSource {
    suspend fun getPokemonSpeciesDetail(species: String, completion: suspend (Result<PokemonSpeciesAPIResponse>) -> Unit)
    suspend  fun getPokemonDetail(name: String, completion: suspend (response: Result<PokemonDetailResponse>) -> Unit)
    suspend  fun getPokemonList(completion: suspend (response: Result<PokemonListResponse>) -> Unit)
}