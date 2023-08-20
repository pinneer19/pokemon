package com.example.pokemon.data.datasource

import com.google.gson.annotations.SerializedName


data class PokemonDto(
    val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val types: List<PokemonType>,
    var sprites: PokemonSprites

)

data class PokemonType(
    val slot: Int?,
    val type: Type?
)

data class Type(
    val name: String?,
    val url: String?
)

data class PokemonSprites(
    @SerializedName("front_default") val frontDefault: String?,
    @SerializedName("front_shiny") val frontShiny: String?,
    @SerializedName("front_female") val frontFemale: String?,
    @SerializedName("front_shiny_female") val frontShinyFemale: String?,
)