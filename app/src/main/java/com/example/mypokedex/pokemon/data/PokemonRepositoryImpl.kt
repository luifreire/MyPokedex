package com.example.mypokedex.pokemon.data

import com.example.mypokedex.pokemon.api.PokemonDataSource
import com.example.mypokedex.pokemon.data.model.PokemonDetailResponse

class PokemonRepositoryImpl constructor(private val api: PokemonDataSource): PokemonRepository {
    override fun getPokemon(name: String, completion: (pokemon: Pokemon?) -> Unit) {
        val pokeName = name.lowercase()
        try {
            api.getPokemonDetail(pokeName) {res: PokemonDetailResponse? ->
                res?.let { pokeResponse ->
                    api.getPokemonSpeciesDetail(pokeName) { speciesResponse ->
                        println(speciesResponse)
                        speciesResponse?.let {
                            var poke = Pokemon(
                                pokeResponse.name,
                                pokeResponse.height.toFloat(),
                                pokeResponse.weight.toFloat(),
                                pokeResponse.abilities.map { ability -> ability.ability.name },
                                it.genderRate.toFloat(),
                                it.captureRate.toFloat(),
                                it.eggGroups?.map { group -> group.name } ?: listOf("")
                            )
                            completion(poke)
                        }

                    }
                }

            }
        }
        catch (e: Exception) {
            println("An error happened while fetching from the API: ${e.message}")
            completion(null)
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