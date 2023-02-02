package com.example.mypokedex.pokemon.data

data class Pokemon(
    val name: String,
    val height: Float,
    val weight: Float,
    val abilities: List<String>,
    val genderRatio: Float,
    val catchRate: Float,
    val eggGroup: List<String>
) {
}


