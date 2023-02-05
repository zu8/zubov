package com.zuas.fintech.zubov.topFilms.data.repositories

import com.zuas.fintech.zubov.topFilms.data.mappers.MovieMapper
import com.zuas.fintech.zubov.topFilms.data.network.ApiService
import com.zuas.fintech.zubov.topFilms.domain.model.Movie
import com.zuas.fintech.zubov.topFilms.domain.model.PaginatedMovies
import com.zuas.fintech.zubov.topFilms.domain.repositories.MovieRepository

class MovieRepositoryImpl(
    private val apiService: ApiService,
    private val mapper: MovieMapper
) : MovieRepository {


    override suspend fun loadMovies(page: Int): PaginatedMovies {
        val result = apiService.loadTopMovies(page = page)
        return if (result.isSuccessful) {
            mapper.mapMoviesResponseDtoToPaginatedMovies(
                page,
                result.body()?.films ?: emptyList()
            )
        } else {
            PaginatedMovies(IMPOSSIBLE_VALUE, emptyList())
        }
    }

    override suspend fun loadSingleMovie(movieId: Int): Movie {
        val result = apiService.loadMovieDetails(movieId = movieId)
        return if (result.isSuccessful) {
            result.body()?.let { mapper.mapSingleMovieDtoToMovie(it) } ?: Movie()
        } else {
            Movie(movieId = IMPOSSIBLE_VALUE)
        }

    }

    override suspend fun addMovie(movie: Movie) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMovie(movie: Movie) {
        TODO("Not yet implemented")
    }

    companion object {
        const val IMPOSSIBLE_VALUE = -1
    }
}