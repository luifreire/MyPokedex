package com.example.mypokedex.pokemon.data.model

data class PokemonSpeciesAPIResponse (
    val baseHappiness: Long,
    val captureRate: Long,
    val color: PKMNMetaData,
    val eggGroups: List<PokemonSpeciesEggGroup>,
    val evolutionChain: PKMNSpeciesEvolutionChain,
    val evolvesFromSpecies: PKMNMetaData,
    val flavorTextEntries: List<PKMNFlavorTextEntry>,
    val formDescriptions: List<String>,
    val formsSwitchable: Boolean,
    val genderRate: Int,
    val genera: List<PKMNGenera>,
    val generation: PKMNMetaData,
    val growthRate: PKMNMetaData,
    val habitat: PKMNMetaData,
    val hasGenderDifferences: Boolean,
    val hatchCounter: Int,
    val id: Int,
    val isBaby: Boolean,
    val isLegendary: Boolean,
    val isMythical: Boolean,
    val name: String,
    val names: List<PKMNLocalizableName>,
    val order: Int,
    val palParkEncounters: List<PKMNPalParkEncounters>,
    val pokedexNumbers: List<PKMNPkDexNumber>,
    val shape: PKMNMetaData,
    val variaties: List<PKMNVariety>
)

data class PokemonSpeciesEggGroup (val name: String, val url: String)
data class PKMNSpeciesEvolutionChain (val url: String)
data class PKMNFlavorTextEntry (
    val flavorText: String,
    val language: PKMNMetaData,
    val version: PKMNMetaData
)
data class PKMNGenera (val genus: String, val language: PKMNMetaData)
data class PKMNLocalizableName (val name: String, val language: PKMNMetaData)
data class PKMNPalParkEncounters (val area: PKMNMetaData, val baseScore: Int, val rate: Int)
data class PKMNPkDexNumber (val entryNumber: Int, val pokedex: PKMNMetaData)
data class PKMNVariety (val isDefault: Boolean, val pokemon: PKMNMetaData)