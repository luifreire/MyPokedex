package com.example.mypokedex.pokemon.data

import com.example.mypokedex.pokemon.api.PokemonDataSource
import com.example.mypokedex.pokemon.data.model.PokemonDetailResponse
import com.example.mypokedex.pokemon.data.model.PokemonListResponse

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
                                pokeResponse.species.name,
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

    override fun getListOfPokemons(callback: (List<String>?) -> Unit) {
        try {
            api.getPokemonList {res: PokemonListResponse? ->
                res?.let {
                    var keep = it.results.map { item -> item.name }
                    callback(keep)
                }
            }
        }
        catch (e: Exception) {
            println("An error happened while fetching from the API: ${e.message}")
            callback(null)
        }
    }
}