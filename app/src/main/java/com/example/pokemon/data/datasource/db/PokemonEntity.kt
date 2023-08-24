package com.example.pokemon.data.datasource.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.pokemon.domain.PokemonSprites
import com.example.pokemon.domain.PokemonType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val types: List<PokemonType>,
    val sprites: PokemonSprites
)

class PokemonTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromPokemonTypeList(types: List<PokemonType>): String {
        return gson.toJson(types)
    }

    @TypeConverter
    fun toPokemonTypeList(typeString: String): List<PokemonType> {
        val listType = object : TypeToken<List<PokemonType>>() {}.type
        return gson.fromJson(typeString, listType)
    }
}

class PokemonSpritesConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromPokemonSprites(sprites: PokemonSprites): String {
        return gson.toJson(sprites)
    }

    @TypeConverter
    fun toPokemonSprites(spriteString: String): PokemonSprites {
        return gson.fromJson(spriteString, PokemonSprites::class.java)
    }
}