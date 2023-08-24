package com.example.pokemon.data.repository

import com.example.pokemon.data.datasource.api.PokemonDto
import com.example.pokemon.data.datasource.api.PokemonListResponse
import com.example.pokemon.data.datasource.api.PokemonApi
import com.example.pokemon.data.datasource.api.PokemonRepository

class PokemonRepositoryImpl(private val pokemonApi: PokemonApi) : PokemonRepository {
    override suspend fun getPokemons(offset: Int, limit: Int): PokemonListResponse {
        return pokemonApi.getPokemons(offset, limit)
    }

    override suspend fun getPokemonInfo(id: Int): PokemonDto {
        return pokemonApi.getPokemonInfo(id)
    }
}