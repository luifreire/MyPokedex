package com.example.mypokedex.pokemon.data

import com.example.mypokedex.pokemon.api.PokemonDataSource
import com.example.mypokedex.pokemon.data.model.PokemonDetailResponse
import com.example.mypokedex.pokemon.data.model.PokemonListResponse
import kotlinx.coroutines.flow.Flow
import com.example.mypokedex.pokemon.data.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class PokemonRepositoryImpl constructor(private val api: PokemonDataSource): PokemonRepository {
    override suspend fun getPokemon(name: String, completion: suspend (pokemon: Flow<Result<Pokemon>?>) -> Unit) {
        val pokeName = name.lowercase()
        completion(flow {
            emit(Result.loading())
            api.getPokemonDetail(pokeName) { detail ->
                if (detail.status == Result.Status.SUCCESS) {
                    api.getPokemonSpeciesDetail(pokeName) { speciesResponse ->
                        if (speciesResponse.status == Result.Status.SUCCESS) {
                            detail.data?.let { pokeResponse ->
                                speciesResponse.data?.let {
                                    var poke = Pokemon(
                                        pokeResponse.name,
                                        it.color.name,
                                        pokeResponse.sprites.other?.home?.frontDefault ?: "",
                                        it.flavorTextEntries[0].flavorText,
                                        pokeResponse.species.name,
                                        pokeResponse.height.toFloat(),
                                        pokeResponse.weight.toFloat(),
                                        pokeResponse.abilities.map { ability -> ability.ability.name },
                                        it.genderRate.toFloat(),
                                        it.captureRate.toFloat(),
                                        it.eggGroups?.map { group -> group.name } ?: listOf(""),
                                        pokeResponse.types
                                    )
                                    emit(Result(
                                        Result.Status.SUCCESS,
                                        poke,
                                        null
                                    ))
                                }
                            }

                        }
                    }
                }

            }
        }.flowOn(Dispatchers.IO))
    }

    override suspend fun getListOfPokemons(callback: suspend (Flow<Result<List<String>>?>) -> Unit) {
            callback(flow {
                emit(Result.loading())
                api.getPokemonList {res ->
                    res.data?.let {
                        var keep = it.results.map { item -> item.name }
                        emit(Result(Result.Status.SUCCESS, keep, null))
                    }
                }
            }.flowOn(Dispatchers.IO))

    }
}