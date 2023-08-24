package com.example.pokemon.data.datasource.mappers

import com.example.pokemon.data.datasource.api.PokemonDto
import com.example.pokemon.data.datasource.db.PokemonEntity
import com.example.pokemon.domain.Pokemon

fun PokemonDto.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        weight = weight,
        height = height,
        types = types,
        sprites = sprites
    )
}

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        weight = weight,
        height = height,
        types = types,
        sprites = sprites
    )
}