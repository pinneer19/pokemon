package com.example.pokemon.data.datasource.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonPreviewEntity(
    val name: String,
    val url: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)