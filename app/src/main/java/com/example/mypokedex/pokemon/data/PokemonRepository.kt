package com.example.mypokedex.pokemon.data

interface PokemonRepository {
    fun getPokemon(name: String): Pokemon?
    fun getListOfPokemons(): List<String>
}


