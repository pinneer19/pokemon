package com.example.pokemon.data.datasource

import com.example.pokemon.data.repository.PokemonApi
import com.example.pokemon.data.repository.PokemonRepository
import com.example.pokemon.data.repository.PokemonRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val pokemonRepository: PokemonRepository
}

class PokemonAppContainer : AppContainer {
    private val BASE_URL = "https://pokeapi.co/api/v2/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitService: PokemonApi by lazy {
        retrofit.create(PokemonApi::class.java)
    }

    override val pokemonRepository: PokemonRepository by lazy {
        PokemonRepositoryImpl(retrofitService)
    }
}