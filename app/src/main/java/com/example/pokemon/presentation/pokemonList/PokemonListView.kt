package com.example.pokemon.presentation.pokemonList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.pokemon.presentation.NetworkUiState
import com.example.pokemon.presentation.PokemonViewModel


@Composable
fun PokemonListView(
    pokemonViewModel: PokemonViewModel,
) {
    val isRefreshing by pokemonViewModel.isLoadingPokemons.collectAsState()

    when (val state = pokemonViewModel.networkUiState) {
        is NetworkUiState.Loading -> LoadingScreen()

        is NetworkUiState.Success -> ResultScreen(
            state.pokemonListResponse
        )

        is NetworkUiState.Error -> ErrorScreen(
            isRefreshing, state.message
        ) {
            pokemonViewModel.getPokemons()
        }
    }

}