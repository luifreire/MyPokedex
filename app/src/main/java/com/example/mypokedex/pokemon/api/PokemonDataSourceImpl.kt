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


    override fun getPokemonSpeciesDetail(species: String): PokemonSpeciesAPIResponse? {
        TODO("Saulo - Precisa ser feito com Callback")
    }

    override fun getPokemonDetail(name: String, completion: (response: PokemonDetailResponse?) -> Unit) {
        val call: Call<PokemonDetailResponse> = pokeApi.pokemon(name)
        call.enqueue(object: Callback<PokemonDetailResponse> {
            override fun onResponse(
                call: Call<PokemonDetailResponse>,
                response: Response<PokemonDetailResponse>
            ) {
                if (response.isSuccessful) {
                    completion(response.body()!!)
                } else {
                    Log.v("retrofit", "failed to fetch pokemon species due to error ${response.errorBody()}")
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

    }
    //TODO("Seguir o exemplo do getPokemonDetail, implementando a nova chamada")

}