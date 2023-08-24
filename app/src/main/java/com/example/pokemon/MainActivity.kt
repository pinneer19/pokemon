package com.example.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokemon.presentation.PokemonDetailViewModel
import com.example.pokemon.presentation.PokemonViewModel
import com.example.pokemon.presentation.pokemonDetails.PokemonDetailView
import com.example.pokemon.presentation.pokemonList.PokemonListView
import com.example.pokemon.ui.theme.PokemonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavRoutes.HOME.name
                ) {
                    composable(NavRoutes.HOME.name) {

                        val viewModel: PokemonViewModel =
                            viewModel(factory = PokemonViewModel.Factory)
                        val pokemons = viewModel.pokemonsPagingFlow.collectAsLazyPagingItems()
                        PokemonListView(viewModel, pokemons, navController)

                    }
                    composable(
                        route = NavRoutes.DETAIL.name + "/{url}",
                        arguments = listOf(
                            navArgument(URL_KEY) {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val pokemonUrl = it.arguments?.getString(URL_KEY)
                        val viewModel: PokemonDetailViewModel =
                            viewModel(factory = PokemonDetailViewModel.Factory)
                        PokemonDetailView(viewModel, checkNotNull(pokemonUrl).parseUrl(), navController)

                    }
                }
            }
        }
    }

    private fun String.parseUrl() = this.dropLast(1).substringAfterLast('/').toInt()

    private val URL_KEY = "url"
}

enum class NavRoutes {
    HOME, DETAIL
}