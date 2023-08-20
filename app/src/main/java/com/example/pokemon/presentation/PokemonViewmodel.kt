package com.example.pokemon.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pokemon.PokemonApplication
import com.example.pokemon.data.datasource.PokemonListResponse
import com.example.pokemon.data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface NetworkUiState {
    data class Success(val pokemonListResponse: PokemonListResponse) : NetworkUiState
    data class Error(val message: String) : NetworkUiState
    object Loading : NetworkUiState
}

class PokemonViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    var networkUiState: NetworkUiState by mutableStateOf(NetworkUiState.Loading)
        private set

    private val _isLoadingPokemons = MutableStateFlow(false)
    val isLoadingPokemons = _isLoadingPokemons.asStateFlow()

    private val DEFAULT_OFFSET = 0
    private val DEFAULT_LIMIT = 20

    init {
        getPokemons()
    }

    fun getPokemons(offset: Int = DEFAULT_OFFSET, limit: Int = DEFAULT_LIMIT) {

        viewModelScope.launch {
            _isLoadingPokemons.value = true
            networkUiState = try {

                val pokemonList = pokemonRepository.getPokemons(offset, limit)
                _isLoadingPokemons.value = false
                NetworkUiState.Success(pokemonList)

            } catch (e: IOException) {
                _isLoadingPokemons.emit(false)
                NetworkUiState.Error("Could not connect to the server. Please check your internet connection and try again")
            } catch (e: HttpException) {
                _isLoadingPokemons.emit(false)
                NetworkUiState.Error("Request failed with ${e.code()}. Please try again later")
            } catch (e: Exception) {
                _isLoadingPokemons.emit(false)
                NetworkUiState.Error("An unknown error occurred. Please try again later")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PokemonApplication)
                val apiRepository = application.container.pokemonRepository
                PokemonViewModel(pokemonRepository = apiRepository)
            }
        }
    }
}