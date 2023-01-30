package com.example.mypokedex.pokemon.api

import com.example.mypokedex.pokemon.data.model.PokemonDetailResponse
import com.example.mypokedex.pokemon.data.model.PokemonSpeciesAPIResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI {
    @GET("pokemon-species/{species}")
    fun species(@Path("species") specie: String): Call<PokemonSpeciesAPIResponse>
    @GET("pokemon/{name}")
    fun pokemon(@Path("name") name: String): Call<PokemonDetailResponse>
    //TODO("Adicionar uma requisição para pokeapi.co/api/v2/pokemon para pegar a lista de nomes")
}

interface PokemonDataSource {
    //TODO("Saulo – adicionar callback nessa requisição")
    fun getPokemonSpeciesDetail(species: String): PokemonSpeciesAPIResponse?
    fun getPokemonDetail(name: String, completion: (response: PokemonDetailResponse?) -> Unit)
    //TODO("Adicionar método para trazer a resposta da API com a lista de nomes, exatamente como ela vem")
    //TODO("Seguir o exemplo do getPokemonDetail")
}