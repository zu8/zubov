package com.zuas.fintech.zubov.topFilms.domain.repositories

import com.zuas.fintech.zubov.topFilms.domain.model.Movie
import com.zuas.fintech.zubov.topFilms.domain.model.PaginatedMovies


interface MovieRepository {

    suspend fun loadMovies(page: Int): PaginatedMovies

    suspend fun loadSingleMovie(movieId: Int): Movie

    suspend fun addMovie(movie: Movie)

    suspend fun deleteMovie(movie: Movie)

}