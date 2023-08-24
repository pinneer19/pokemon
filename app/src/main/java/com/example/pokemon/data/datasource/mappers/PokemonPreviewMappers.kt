package com.example.pokemon.data.datasource.mappers

import com.example.pokemon.data.datasource.api.PokemonPreviewDto
import com.example.pokemon.data.datasource.db.PokemonPreviewEntity
import com.example.pokemon.domain.PokemonPreview

fun PokemonPreviewDto.toPokemonPreviewEntity(): PokemonPreviewEntity {
    return PokemonPreviewEntity(
        name = name,
        url = url
    )
}

fun PokemonPreviewEntity.toPokemonPreview(): PokemonPreview {
    return PokemonPreview(
        name = name,
        url = url
    )
}