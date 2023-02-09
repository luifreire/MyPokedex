package com.example.mypokedex.pokemon.data

import com.example.mypokedex.pokemon.data.model.Type

data class Pokemon(
    val name: String,
    val color: String,
    val imageUrl: String,
    val quote: String,
    val species: String,
    val height: Float,
    val weight: Float,
    val abilities: List<String>,
    val genderRatio: Float,
    val catchRate: Float,
    val eggGroup: List<String>,
    val types: List<Type>
) {
}


