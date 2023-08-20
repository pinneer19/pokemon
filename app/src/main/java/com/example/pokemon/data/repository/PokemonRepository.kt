package com.example.pokemon.data.repository

import com.example.pokemon.data.datasource.PokemonDto
import com.example.pokemon.data.datasource.PokemonListResponse


interface PokemonRepository {

    suspend fun getPokemons(offset: Int, limit: Int): PokemonListResponse

    suspend fun getPokemInfo(id: Int): PokemonDto

}

class PokemonRepositoryImpl(private val pokemonApi: PokemonApi) : PokemonRepository {
    override suspend fun getPokemons(offset: Int, limit: Int): PokemonListResponse {
        return pokemonApi.getPokemons(offset, limit)
    }

    override suspend fun getPokemInfo(id: Int): PokemonDto {
        return pokemonApi.getPokemInfo(id)
    }
}