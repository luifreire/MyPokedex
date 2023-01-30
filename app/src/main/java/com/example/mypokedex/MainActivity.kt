package com.example.mypokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mypokedex.pokemon.api.PokemonDataSourceImpl

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        PokemonDataSourceImpl().getPokemonDetail("bulbasaur") { response ->
            println(response)
        }
    }
}