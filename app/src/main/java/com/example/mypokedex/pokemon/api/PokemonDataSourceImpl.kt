package com.example.mypokedex.pokemon.api

import android.util.Log
import com.example.mypokedex.pokemon.data.model.PokemonDetailResponse
import com.example.mypokedex.pokemon.data.model.PokemonSpeciesAPIResponse
import com.example.mypokedex.pokemon.data.model.PokemonListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonDataSourceImpl: PokemonDataSource {

    private val apiURL = "https://pokeapi.co/api/v2/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(this.apiURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val pokeApi = retrofit.create(PokemonAPI::class.java)


    override fun getPokemonSpeciesDetail(species: String, completion: (PokemonSpeciesAPIResponse?) -> Unit) {
        val call: Call<PokemonSpeciesAPIResponse> = pokeApi.species(species)
        call.enqueue(object: Callback<PokemonSpeciesAPIResponse> {
            override fun onResponse(
                call: Call<PokemonSpeciesAPIResponse>,
                response: Response<PokemonSpeciesAPIResponse>
            ) {
                if (response.isSuccessful) {
                    completion(response.body()!!)
                } else {
                    Log.v("retrofit", "failed to fetch pokemon species due to error ${response.errorBody()}")
                    completion(null)
                }
            }

            override fun onFailure(call: Call<PokemonSpeciesAPIResponse>, t: Throwable) {
                Log.v("retrofit", "failed to fetch pokemon detail due to error: ${t.message}")
                completion(null)
            }
        })
    }

    override fun getPokemonDetail(name: String, completion: (response: PokemonDetailResponse?) -> Unit) {
        var pokeName = name
        if (name == "deoxys") {
            pokeName = "deoxys-normal"
        }
        val call: Call<PokemonDetailResponse> = pokeApi.pokemon(pokeName)
        call.enqueue(object: Callback<PokemonDetailResponse> {
            override fun onResponse(
                call: Call<PokemonDetailResponse>,
                response: Response<PokemonDetailResponse>
            ) {
                println(response)
                if (response.isSuccessful) {
                    completion(response.body()!!)
                } else {
                    Log.v("retrofit", "failed to fetch pokemon species due to error ${response.errorBody().toString()}")
                    completion(null)
                }
            }

            override fun onFailure(call: Call<PokemonDetailResponse>, t: Throwable) {
                Log.v("retrofit", "failed to fetch pokemon detail due to error: ${t.message}")
                completion(null)
            }
        })
    }

    override fun getPokemonList(completion: (response: PokemonListResponse?) -> Unit) {
        val call: Call<PokemonListResponse> = pokeApi.pokemonList()
        call.enqueue(object: Callback<PokemonListResponse> {
            override fun onResponse(
                call: Call<PokemonListResponse>,
                response: Response<PokemonListResponse>
            ) {
                if (response.isSuccessful) {
                    completion(response.body()!!)
                } else {
                    Log.v("retrofit", "failed to fetch pokemon list due to error ${response.errorBody()}")
                    completion(null)
                }
            }

            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                Log.v("retrofit", "failed to fetch pokemon detail due to error: ${t.message}")
                completion(null)
            }
        })
    }
    //TODO("Seguir o exemplo do getPokemonDetail, implementando a nova chamada")

}