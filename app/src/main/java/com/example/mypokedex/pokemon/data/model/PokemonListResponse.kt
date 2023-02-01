package com.example.mypokedex.pokemon.data.model

data class PokemonListResponse (
    val count: Long,
    val next: String?,
    val previous: String?,
    val results: List<PKMNMetaData>
    )

