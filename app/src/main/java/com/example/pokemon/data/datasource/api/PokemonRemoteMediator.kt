package com.example.pokemon.data.datasource.api

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.pokemon.data.datasource.db.PokemonDatabase
import com.example.pokemon.data.datasource.db.PokemonPreviewEntity
import com.example.pokemon.data.datasource.mappers.toPokemonPreviewEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val pokemonDb: PokemonDatabase,
    private val pokemonRepo: PokemonRepository
): RemoteMediator<Int, PokemonPreviewEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonPreviewEntity>
    ): MediatorResult {

        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) {
                        0
                    } else {
                        (lastItem.url.dropLast(1).substringAfterLast('/').toInt() / state.config.pageSize) + 1
                    }

                }
            }

            val pokemons = pokemonRepo.getPokemons(
                offset = loadKey,
                limit = state.config.pageSize
            )

            pokemonDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    pokemonDb.dao.clearAll()
                }
                val pokemonPreviewEntities = pokemons.results.map { it.toPokemonPreviewEntity() }
                pokemonDb.dao.upsertAll(pokemonPreviewEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = pokemons.results.isEmpty()
            )
        }
        catch (e: IOException) {
            MediatorResult.Error(e)
        }
        catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}