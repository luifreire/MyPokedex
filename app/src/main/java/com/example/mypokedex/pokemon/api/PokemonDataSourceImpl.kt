package com.example.mypokedex.pokemon.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.mypokedex.pokemon.data.model.*
import java.io.IOException

class PokemonDataSourceImpl: PokemonDataSource {

    private val apiURL = "https://pokeapi.co/api/v2/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(this.apiURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val pokeApi = retrofit.create(PokemonAPI::class.java)


    override suspend fun getPokemonSpeciesDetail(
        species: String,
        completion: suspend (Result<PokemonSpeciesAPIResponse>) -> Unit
    ) {
        val response = getResponse({ pokeApi.species(species) }, defaultErrorMessage = "Failed to fetch pokemon species due to error")
        completion(response)
    }

    override suspend fun getPokemonDetail(name: String, completion: suspend (response: Result<PokemonDetailResponse>) -> Unit) {
        var pokeName = name
        if (name == "deoxys") {
            pokeName = "deoxys-normal"
        }
        val response = getResponse({ pokeApi.pokemon(pokeName) }, defaultErrorMessage = "Failed to fetch pokemon detail due to error")
        completion(response)
    }

    override suspend fun getPokemonList(completion: suspend (response: Result<PokemonListResponse>) -> Unit) {
        val response = getResponse({pokeApi.pokemonList()}, defaultErrorMessage = "Failed to fetch pokemon list due to error")
        completion(response)
    }
    private suspend fun <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): Result<T> {
        return try {
            println("I'm working in thread ${Thread.currentThread().name}")
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body())
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Result.error(errorResponse?.status_message ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            Result.error("Unknown Error", null)
        }
    }
}

/**
 * parses error response body
 */
object ErrorUtils {

    fun parseError(response: Response<*>, retrofit: Retrofit): Error? {
        val converter = retrofit.responseBodyConverter<Error>(Error::class.java, arrayOfNulls(0))
        return try {
            converter.convert(response.errorBody()!!)
        } catch (e: IOException) {
            Error()
        }
    }
}