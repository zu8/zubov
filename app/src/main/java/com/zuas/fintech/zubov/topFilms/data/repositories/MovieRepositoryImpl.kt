package com.zuas.fintech.zubov.topFilms.data.repositories

import com.zuas.fintech.zubov.topFilms.data.mappers.MovieMapper
import com.zuas.fintech.zubov.topFilms.data.network.ApiService
import com.zuas.fintech.zubov.topFilms.domain.model.Movie
import com.zuas.fintech.zubov.topFilms.domain.model.PaginatedMovies
import com.zuas.fintech.zubov.topFilms.domain.repositories.MovieRepository
import retrofit2.Response
import java.io.IOException

class MovieRepositoryImpl(
    private val apiService: ApiService,
    private val mapper: MovieMapper
): MovieRepository {


    override suspend fun getMovies(page: Int): PaginatedMovies {
        val result = apiService.loadTopMovies(page=page)
        if (result.isSuccessful){
            return mapper.mapMoviesResponseDtoToPaginatedMovies(
                page,
                result.body()?.films ?: emptyList())
        }
        else{
            return PaginatedMovies(-1, emptyList())
            //throw IOException(result.errorBody().toString())
        }
    }

    override suspend fun getMovie(movieId: Int): Movie {
        TODO("Not yet implemented")
    }

    override suspend fun addMovie(movie: Movie) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMovie(movie: Movie) {
        TODO("Not yet implemented")
    }
}