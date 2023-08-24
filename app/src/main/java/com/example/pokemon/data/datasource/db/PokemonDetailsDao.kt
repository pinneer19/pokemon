package com.example.pokemon.data.datasource.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PokemonDetailsDao {
    @Upsert
    suspend fun upsertPokemonDetails(pokemon: PokemonEntity)

    @Query("SELECT * FROM pokemonentity WHERE id = :id")
    suspend fun getPokemonDetailsById(id: Int): PokemonEntity?
}