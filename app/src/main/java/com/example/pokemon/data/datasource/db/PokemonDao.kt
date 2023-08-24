package com.example.pokemon.data.datasource.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PokemonDao {
    @Upsert(entity = PokemonPreviewEntity::class)
    suspend fun upsertAll(pokemons: List<PokemonPreviewEntity>)

    @Query("SELECT * FROM pokemonpreviewentity")
    fun pagingSource(): PagingSource<Int, PokemonPreviewEntity>

    @Query("DELETE FROM pokemonpreviewentity")
    suspend fun clearAll()
}