package com.example.pokemon.data.repository

import com.example.pokemon.data.datasource.PokemonDto
import com.example.pokemon.data.datasource.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonListResponse

    @GET("item/{id}")
    suspend fun getPokemInfo(
        @Path("id") id: Int
    ) : PokemonDto

}