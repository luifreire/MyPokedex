package com.example.mypokedex.pokemon.data.model

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse (
    val abilities: List<Ability>,
    @SerializedName("base_experience")
    val baseExperience: Long,
    val forms: List<PKMNMetaData>,
    @SerializedName("game_indices")
    val gameIndices: List<GameIndex>,
    val height: Long,
    @SerializedName("held_items")
    val heldItems: List<Any?>,
    val id: Long,
    @SerializedName("is_default")
    val isDefault: Boolean,
    @SerializedName("location_area_encounters")
    val locationAreaEncounters: String,
    val moves: List<Move>,
    val name: String,
    val order: Long,
    @SerializedName("past_types")
    val pastTypes: List<Any?>,
    val species: PKMNMetaData,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Long
)

data class PKMNMetaData (val name: String, val url: String)

data class Ability (
    val ability: PKMNMetaData,
    @SerializedName("is_hidden")
    val isHidden: Boolean,
    val slot: Long
)

data class GameIndex (
    @SerializedName("game_index")
    val gameIndex: Long,
    val version: PKMNMetaData
)

data class Move (
    val move: PKMNMetaData,
    @SerializedName("version_group_details")
    val versionGroupDetails: List<VersionGroupDetail>
)

data class VersionGroupDetail (
    @SerializedName("level_learned_at")
    val levelLearnedAt: Long,
    @SerializedName("move_learn_method")
    val moveLearnMethod: PKMNMetaData,
    @SerializedName("version_group")
    val versionGroup: PKMNMetaData
)

data class GenerationV (
    @SerializedName("black_white")
    val blackWhite: Sprites
)

data class GenerationIv (
    @SerializedName("diamond_pearl")
    val diamondPearl: Sprites,
    @SerializedName("heartgold_soulsilver")
    val heartgoldSoulsilver: Sprites,
    val platinum: Sprites
)

data class Versions (
    val generationI: GenerationI,
    val generationIi: GenerationIi,
    val generationIii: GenerationIii,
    val generationIv: GenerationIv,
    val generationV: GenerationV,
    val generationVi: Map<String, Home>,
    val generationVii: GenerationVii,
    val generationViii: GenerationViii
)

data class Sprites (
    @SerializedName("back_default")
    val backDefault: String,
    @SerializedName("back_female")
    val backFemale: Any? = null,
    @SerializedName("back_shiny")
    val backShiny: String,
    @SerializedName("back_shiny_female")
    val backShinyFemale: Any? = null,
    @SerializedName("front_default")
    val frontDefault: String,
    @SerializedName("front_female")
    val frontFemale: Any? = null,
    @SerializedName("front_shiny")
    val frontShiny: String,
    @SerializedName("front_shiny_female")
    val frontShinyFemale: Any? = null,
    val other: Other? = null,
    val versions: Versions? = null,
    val animated: Sprites? = null
)

data class GenerationI (
    @SerializedName("red_blue")
    val redBlue: RedBlue,
    val yellow: RedBlue
)

data class RedBlue (
    @SerializedName("back_default")
    val backDefault: String,
    @SerializedName("back_gray")
    val backGray: String,
    @SerializedName("back_transparent")
    val backTransparent: String,
    @SerializedName("front_default")
    val frontDefault: String,
    @SerializedName("front_gray")
    val frontGray: String,
    @SerializedName("front_transparent")
    val frontTransparent: String
)

data class GenerationIi (
    val crystal: Crystal,
    val gold: Gold,
    val silver: Gold
)

data class Crystal (
    val backDefault: String,
    val backShiny: String,
    val backShinyTransparent: String,
    val backTransparent: String,
    val frontDefault: String,
    val frontShiny: String,
    val frontShinyTransparent: String,
    val frontTransparent: String
)

data class Gold (
    val backDefault: String,
    val backShiny: String,
    val frontDefault: String,
    val frontShiny: String,
    val frontTransparent: String? = null
)

data class GenerationIii (
    val emerald: OfficialArtwork,
    @SerializedName("firered_leafgreen")
    val fireredLeafgreen: Gold,
    @SerializedName("ruby_sapphire")
    val rubySapphire: Gold
)

data class OfficialArtwork (
    val frontDefault: String,
    val frontShiny: String
)

data class Home (
    @SerializedName("front_default")
    val frontDefault: String,
    @SerializedName("front_female")
    val frontFemale: Any? = null,
    @SerializedName("front_shiny")
    val frontShiny: String,
    @SerializedName("front_shiny_female")
    val frontShinyFemale: Any? = null
)

data class GenerationVii (
    val icons: DreamWorld,
    val ultraSunUltraMoon: Home
)

data class DreamWorld (
    val frontDefault: String,
    val frontFemale: Any? = null
)

data class GenerationViii (
    val icons: DreamWorld
)

data class Other (
    val dreamWorld: DreamWorld,
    val home: Home,
    val officialArtwork: OfficialArtwork
)

data class Stat (
    val baseStat: Long,
    val effort: Long,
    val stat: PKMNMetaData
)

data class Type (
    val slot: Long,
    val type: PKMNMetaData
)