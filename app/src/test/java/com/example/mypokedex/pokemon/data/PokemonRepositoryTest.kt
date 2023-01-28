package com.example.mypokedex.pokemon.data

import org.junit.Test
import org.junit.Assert.*

class PokemonRepositoryTest {
    private val sut = PokemonRepositoryImpl()

    @Test
    fun testGetPokemon() {
        val pokemon = Pokemon("Bulbasaur",7.0f,69f, listOf("overgrow", "chlorophyll"),1f,45f,
            listOf("monster","plant")
        )
        val returnedPokemon = sut.getPokemon("Bulbasaur")
        assertEquals(pokemon, returnedPokemon)
    }

    @Test
    fun testGetListOfPokemons() {
        val pokemons = listOf<String>("Bulbasaur","Eve","Pikachu","Charizard","MewTwo","Pichu")

        val returnedPokemons = sut.getListOfPokemons()
        assertEquals(pokemons, returnedPokemons)
    }

}
