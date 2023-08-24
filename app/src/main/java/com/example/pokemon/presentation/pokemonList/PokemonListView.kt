package com.example.pokemon.presentation.pokemonList

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.pokemon.NavRoutes
import com.example.pokemon.R
import com.example.pokemon.domain.PokemonPreview
import com.example.pokemon.presentation.PokemonViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun PokemonListView(
    pokemonViewModel: PokemonViewModel,
    pokemons: LazyPagingItems<PokemonPreview>,
    navHostController: NavController
) {
    val isRefreshing by pokemonViewModel.isLoadingPokemons.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = pokemons.loadState) {
        if (pokemons.loadState.refresh is LoadState.Error) {
            Log.e(
                "ERROR",
                "Error: " + (pokemons.loadState.refresh as LoadState.Error).error.message
            )
            Toast.makeText(
                context,
                "Check your internet connection",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    when (pokemons.loadState.refresh) {
        is LoadState.Loading -> LoadingScreen()

        else -> {
            if (pokemons.itemCount == 0) {
                ErrorScreen(
                    isRefreshing = isRefreshing,
                    error = stringResource(R.string.connection_error)
                ) {
                    pokemons.refresh()
                }
            }
            else {
                ResultScreen(
                    pokemonList = pokemons,
                    onPokemonClicked = { url ->
                        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                        navHostController.navigate(NavRoutes.DETAIL.name + "/$encodedUrl")
                    }
                )
            }
        }

    }
}