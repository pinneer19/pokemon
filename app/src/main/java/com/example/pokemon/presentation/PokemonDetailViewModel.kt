package com.example.pokemon.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pokemon.PokemonApplication
import com.example.pokemon.data.datasource.api.PokemonRepository
import com.example.pokemon.data.datasource.db.PokemonDatabase
import com.example.pokemon.data.datasource.mappers.toPokemon
import com.example.pokemon.data.datasource.mappers.toPokemonEntity
import com.example.pokemon.domain.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface PokemonDetailState {
    data class Success(val pokemonDetails: Pokemon) : PokemonDetailState
    data class Error(val message: String) : PokemonDetailState
    object Loading : PokemonDetailState
}

class PokemonDetailViewModel(
    private val pokemonRepository: PokemonRepository,
    private val pokemonDatabase: PokemonDatabase
) : ViewModel() {
    var pokemonDetailState: PokemonDetailState by mutableStateOf(PokemonDetailState.Loading)
        private set

    private val _isLoadingPokemon = MutableStateFlow(false)
    val isLoadingPokemon = _isLoadingPokemon.asStateFlow()

    fun getPokemonInfo(id: Int) {

        viewModelScope.launch {
            _isLoadingPokemon.value = true

            val cachedPokemonDetails = pokemonDatabase.detailsDao.getPokemonDetailsById(id)
            if (cachedPokemonDetails != null) {
                pokemonDetailState = PokemonDetailState.Success(cachedPokemonDetails.toPokemon())
                _isLoadingPokemon.emit(false)
                return@launch
            }
            pokemonDetailState = try {
                val pokemonDetails = pokemonRepository.getPokemonInfo(id)
                pokemonDatabase.detailsDao.upsertPokemonDetails(pokemonDetails.toPokemonEntity())
                _isLoadingPokemon.emit(false)
                PokemonDetailState.Success(pokemonDetails.toPokemonEntity().toPokemon())
            } catch (e: IOException) {
                _isLoadingPokemon.emit(false)
                PokemonDetailState.Error("Could not connect to the server. Please check your internet connection and try again")
            } catch (e: HttpException) {
                _isLoadingPokemon.emit(false)
                PokemonDetailState.Error("Request failed with ${e.code()}. Please try again later")
            } catch (e: Exception) {
                _isLoadingPokemon.emit(false)
                PokemonDetailState.Error("An unknown error occurred. Please try again later")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PokemonApplication)
                val apiRepository = application.container.pokemonRepository
                val database = application.container.pokemonDatabase
                PokemonDetailViewModel(
                    pokemonRepository = apiRepository,
                    pokemonDatabase = database
                )
            }
        }
    }
}