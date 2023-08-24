package com.example.pokemon.presentation.pokemonDetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.pokemon.presentation.PokemonDetailState
import com.example.pokemon.presentation.PokemonDetailViewModel
import com.example.pokemon.presentation.pokemonList.ErrorScreen
import com.example.pokemon.presentation.pokemonList.LoadingScreen

@Composable
fun PokemonDetailView(viewModel: PokemonDetailViewModel, id: Int, navController: NavController) {
    val isRefreshing by viewModel.isLoadingPokemon.collectAsState()

    viewModel.getPokemonInfo(id)
    when (val state = viewModel.pokemonDetailState) {
        is PokemonDetailState.Loading -> LoadingScreen()

        is PokemonDetailState.Success -> PokemonInfoScreen(
            state.pokemonDetails
        ) {
            navController.popBackStack()
        }

        is PokemonDetailState.Error -> ErrorScreen(
            isRefreshing, state.message
        ) {
            viewModel.getPokemonInfo(id)
        }
    }
}


