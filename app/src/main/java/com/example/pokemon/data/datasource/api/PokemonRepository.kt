package com.example.pokemon.data.datasource.api

interface PokemonRepository {

    suspend fun getPokemons(offset: Int, limit: Int): PokemonListResponse

    suspend fun getPokemonInfo(id: Int): PokemonDto

}