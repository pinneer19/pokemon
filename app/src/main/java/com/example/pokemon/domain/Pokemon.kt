package com.example.pokemon.domain

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val types: List<PokemonType>,
    val sprites: PokemonSprites
) {
    fun selectPokemonFrontSprite(): String {
        return listOf(
            sprites.frontDefault,
            sprites.frontShiny,
            sprites.frontFemale,
            sprites.frontShinyFemale
        )
            .firstOrNull { it != null } ?: ""
    }

    fun getStringTypes(): String {
        return this.types.filter { it.type?.name != null }.joinToString { it.type!!.name!! }
    }
}

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