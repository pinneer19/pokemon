package com.example.pokemon.presentation.pokemonList

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.pokemon.data.datasource.PokemonPreview


@Composable
fun ResultScreen(pokemonList: LazyPagingItems<PokemonPreview>) {
    val context = LocalContext.current
    LazyColumn(
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {


        items(count = pokemonList.itemCount) { index ->
            val pokemon = pokemonList[index]

            pokemon?.let {
                PokemonCard(pokemon = pokemon, modifier = Modifier.fillMaxWidth()) {
                    Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
                }
            }

        }
        when (pokemonList.loadState.append) {
            is LoadState.Error -> {

            }

            LoadState.Loading -> {
                item {
                    LoadingPokemon()
                }
            }

            is LoadState.NotLoading -> Unit
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonCard(pokemon: PokemonPreview, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val a = rememberRipple()
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = modifier.height(50.dp),
        onClick = onClick
    )
    {
        Text(
            text = pokemon.name,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
fun LoadingPokemon(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(42.dp)
                .padding(8.dp),
            strokeWidth = 5.dp
        )
    }
}