package com.example.pokemon.data.datasource.api

data class PokemonListResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<PokemonPreviewDto>
)

data class PokemonPreviewDto(
    val name: String,
    val url: String
)