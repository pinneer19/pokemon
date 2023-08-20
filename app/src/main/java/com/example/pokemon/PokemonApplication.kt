package com.example.pokemon

import android.app.Application
import com.example.pokemon.data.datasource.AppContainer
import com.example.pokemon.data.datasource.PokemonAppContainer

class PokemonApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = PokemonAppContainer()
    }
}