package com.example.pokemon.data.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [PokemonPreviewEntity::class, PokemonEntity::class],
    version = 1
)
@TypeConverters(PokemonTypeConverter::class, PokemonSpritesConverter::class)
abstract class PokemonDatabase: RoomDatabase() {
    abstract val dao: PokemonDao
    abstract val detailsDao: PokemonDetailsDao
}