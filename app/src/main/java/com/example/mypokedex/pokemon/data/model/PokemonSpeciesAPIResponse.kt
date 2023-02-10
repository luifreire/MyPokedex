package com.example.mypokedex.pokemon.data.model

import com.google.gson.annotations.SerializedName

data class PokemonSpeciesAPIResponse (
    @SerializedName("base_happiness")
    val baseHappiness: Long,
    @SerializedName("capture_rate")
    val captureRate: Long,
    val color: PKMNMetaData,
    @SerializedName("egg_groups")
    val eggGroups: List<PokemonSpeciesEggGroup>?,
    @SerializedName("evolution_chain")
    val evolutionChain: PKMNSpeciesEvolutionChain,
    @SerializedName("evolves_from_species")
    val evolvesFromSpecies: PKMNMetaData,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<PKMNFlavorTextEntry>,
    @SerializedName("form_descriptions")
    val formDescriptions: List<FormDescription>,
    @SerializedName("forms_switchable")
    val formsSwitchable: Boolean,
    @SerializedName("gender_rate")
    val genderRate: Int,
    val genera: List<PKMNGenera>,
    val generation: PKMNMetaData,
    @SerializedName("growth_rate")
    val growthRate: PKMNMetaData,
    val habitat: PKMNMetaData,
    @SerializedName("has_gender_differences")
    val hasGenderDifferences: Boolean,
    @SerializedName("hatch_counter")
    val hatchCounter: Int,
    val id: Int,
    @SerializedName("is_baby")
    val isBaby: Boolean,
    @SerializedName("is_legendary")
    val isLegendary: Boolean,
    @SerializedName("is_mythical")
    val isMythical: Boolean,
    val name: String,
    val names: List<PKMNLocalizableName>,
    val order: Int,
    @SerializedName("pal_park_encounters")
    val palParkEncounters: List<PKMNPalParkEncounters>,
    @SerializedName("pokedex_numbers")
    val pokedexNumbers: List<PKMNPkDexNumber>,
    val shape: PKMNMetaData,
    val varieties: List<PKMNVariety>
)

data class PokemonSpeciesEggGroup (val name: String, val url: String)
data class  PKMNSpeciesEvolutionChain (val url: String)
data class PKMNFlavorTextEntry (
    @SerializedName("flavor_text")
    val flavorText: String,
    val language: PKMNMetaData,
    val version: PKMNMetaData
)
data class PKMNGenera (val genus: String, val language: PKMNMetaData)
data class PKMNLocalizableName (val name: String, val language: PKMNMetaData)
data class PKMNPalParkEncounters (val area: PKMNMetaData, @SerializedName("base_score") val baseScore: Int, val rate: Int)
data class PKMNPkDexNumber (@SerializedName("entry_number") val entryNumber: Int, val pokedex: PKMNMetaData)
data class PKMNVariety (@SerializedName("is_default") val isDefault: Boolean, val pokemon: PKMNMetaData)

data class FormDescription (val description: String, val language: PKMNMetaData)