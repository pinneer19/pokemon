package com.example.pokemon.data.datasource.api

import com.example.pokemon.domain.PokemonSprites
import com.example.pokemon.domain.PokemonType

data class PokemonDto(
    val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val types: List<PokemonType>,
    val sprites: PokemonSprites
)