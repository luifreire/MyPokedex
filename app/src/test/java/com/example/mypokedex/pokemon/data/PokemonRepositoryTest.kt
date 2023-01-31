package com.example.mypokedex.pokemon.data

import com.example.mypokedex.pokemon.api.*
import com.example.mypokedex.pokemon.data.model.*
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import org.junit.Test
import org.junit.Assert.*

class PokemonRepositoryTest {
    private val dataSource = mockk<PokemonDataSource>(relaxed = true)
    private val sut = PokemonRepositoryImpl(dataSource)

    @Test
    fun testGetPokemon() {
        val pokemon = Pokemon("bulbasaur",0f,0f, listOf(""),1f,45f,
            listOf("monster","plant")
        )
        coEvery { dataSource.getPokemonSpeciesDetail("bulbasaur", any()) } answers {
            mockSpeciesAPIResponse()
        }
        sut.getPokemon("Bulbasaur") {returnedPokemon: Pokemon? ->
            assertEquals(pokemon, returnedPokemon)
        }
    }

    @Test
    fun testGetListOfPokemons() {
        val pokemons = listOf("Bulbasaur","Eve","Pikachu","Charizard","MewTwo","Pichu")
        val returnedPokemons = sut.getListOfPokemons()
        assertEquals(pokemons, returnedPokemons)
    }

    companion object {
        fun mockSpeciesAPIResponse() = PokemonSpeciesAPIResponse(
            49,
            45,
            PKMNMetaData("green", ""),
            listOf(PokemonSpeciesEggGroup("monster", ""), PokemonSpeciesEggGroup("plant", "")),
            PKMNSpeciesEvolutionChain(""),
            PKMNMetaData("", ""),
            listOf(PKMNFlavorTextEntry("", PKMNMetaData("", ""), PKMNMetaData("", ""))),
            listOf(""),
            false,
            1,
            listOf(PKMNGenera("seed", PKMNMetaData("", ""))),
            PKMNMetaData("generation-i", ""),
            PKMNMetaData("medium slow", ""),
            PKMNMetaData("forest", ""),
            false,
            20,
            1,
            false,
            false,
            false,
            "bulbasaur",
            listOf(PKMNLocalizableName("Bulbassauro", PKMNMetaData("pt-br", ""))),
            1,
            listOf(PKMNPalParkEncounters(PKMNMetaData("field", ""), 50, 30)),
            listOf(PKMNPkDexNumber(3, PKMNMetaData("kanto", ""))),
            PKMNMetaData("", ""),
            listOf(PKMNVariety(true, PKMNMetaData("bulbasaur", "")))
        )
    }

}
