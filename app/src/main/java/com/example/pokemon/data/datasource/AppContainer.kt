package com.example.pokemon.data.datasource

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.pokemon.data.datasource.api.PokemonApi
import com.example.pokemon.data.datasource.api.PokemonRemoteMediator
import com.example.pokemon.data.datasource.api.PokemonRepository
import com.example.pokemon.data.datasource.db.PokemonDatabase
import com.example.pokemon.data.datasource.db.PokemonPreviewEntity
import com.example.pokemon.data.repository.PokemonRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val pokemonRepository: PokemonRepository
    val pokemonDatabase: PokemonDatabase
    val pokemonPager: Pager<Int, PokemonPreviewEntity>
}

class PokemonAppContainer(applicationContext: Context) : AppContainer {
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

    override val pokemonDatabase: PokemonDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            PokemonDatabase::class.java,
            "beers.db"
        ).build()
    }

    @OptIn(ExperimentalPagingApi::class)
    override val pokemonPager: Pager<Int, PokemonPreviewEntity> =
        Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = PokemonRemoteMediator(
                pokemonDb = pokemonDatabase,
                pokemonRepo = pokemonRepository
            ),
            pagingSourceFactory = {
                pokemonDatabase.dao.pagingSource()
            }
        )
}