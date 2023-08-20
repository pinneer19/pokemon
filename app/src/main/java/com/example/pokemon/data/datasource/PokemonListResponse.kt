package com.example.pokemon.data.datasource

data class PokemonListResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<PokemonPreview>

)

data class PokemonPreview(
    val name: String,
    val url: String
)