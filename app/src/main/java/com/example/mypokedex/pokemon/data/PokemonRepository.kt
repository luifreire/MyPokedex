package com.example.mypokedex.pokemon.data

interface PokemonRepository {
    fun getPokemon(name: String, completion: (pokemon: Pokemon?) -> Unit)
    fun getListOfPokemons(callback: (List<String>?) -> Unit)
}


