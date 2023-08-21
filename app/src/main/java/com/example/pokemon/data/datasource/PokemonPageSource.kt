package com.example.pokemon.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemon.data.repository.PokemonApi
import com.example.pokemon.data.repository.PokemonRepository
import retrofit2.HttpException
import java.io.IOException

class PokemonPageSource(
    private val pokemonRepository: PokemonRepository,
    private val pageSize: Int
): PagingSource<Int, PokemonPreview>() {

    override fun getRefreshKey(state: PagingState<Int, PokemonPreview>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonPreview> {
        val pageIndex = params.key ?: 1

        return try {
            val offset = (pageIndex - 1) * pageSize
            val response = pokemonRepository.getPokemons(offset, params.loadSize)

            return LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = if(response.results.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
            )
        }
        catch (e: IOException) {
            LoadResult.Error(throwable = e)
        }
        catch (e: HttpException) {
            LoadResult.Error(throwable = e)
        }
    }
}