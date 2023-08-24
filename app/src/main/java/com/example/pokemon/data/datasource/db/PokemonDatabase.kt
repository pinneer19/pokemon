package com.example.pokemon.data.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PokemonPreviewEntity::class],
    version = 1
)
abstract class PokemonDatabase: RoomDatabase() {
    abstract val dao: PokemonDao
}