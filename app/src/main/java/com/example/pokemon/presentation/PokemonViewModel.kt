package com.example.pokemon.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.pokemon.PokemonApplication
import com.example.pokemon.data.datasource.db.PokemonPreviewEntity
import com.example.pokemon.data.datasource.mappers.toPokemonPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class PokemonViewModel(
    private val pager: Pager<Int, PokemonPreviewEntity>,
) : ViewModel() {

    private val _isLoadingPokemons = MutableStateFlow(false)
    val isLoadingPokemons = _isLoadingPokemons.asStateFlow()

    val pokemonsPagingFlow = pager.flow.map { data -> data.map { it.toPokemonPreview() } }.cachedIn(viewModelScope)

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PokemonApplication)
                val pager = application.container.pokemonPager
                PokemonViewModel(pager = pager)
            }
        }
    }
}