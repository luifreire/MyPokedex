package com.example.mypokedex.pokemon.data

import com.example.mypokedex.pokemon.api.PokemonDataSource

class PokemonRepositoryImpl constructor(private val api: PokemonDataSource): PokemonRepository {
    override fun getPokemon(name: String): Pokemon? {
        val pokeName = name.lowercase()
        try {
            val response = api.getPokemonSpeciesDetail(pokeName)!!
            return Pokemon(
                response.name,
                0f,
                0f,
                listOf(""),
                response.genderRate.toFloat(),
                response.captureRate.toFloat(),
                response.eggGroups.map { eggGroup -> eggGroup.name }
            )
        }
        catch (e: Exception) {
            println("An error happened while fetching from the API: ${e.message}")
            return null
        }
    }

    override fun getListOfPokemons(): List<String> {
//        try {
//            val response = api.getPokemonList()!!
//            return PokemonNames(
//                response.pokemonName,
//                response.pokemonName,
//                response.pokemonName,
//                response.pokemonName,
//                response.pokemonName,
//                response.pokemonName,
//                response.pokemonName,
//                response.pokemonName,
//                response.pokemonName,
//                response.pokemonName
//            )
//        }
//        catch (e: Exception) {
//            println("An error happened while fetching from the API: ${e.message}")
//            return null
//        }
        return listOf("")
        TODO("Chamar método criado no data source para pegar a resposta da API")
        TODO("Filtrar para ficar só uma lista de nomes como string")
        TODO("Acrescentar callback nesse método e atualizar o teste")
    }
}