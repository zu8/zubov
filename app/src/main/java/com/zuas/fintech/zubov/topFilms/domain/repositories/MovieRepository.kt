package com.zuas.fintech.zubov.topFilms.domain.repositories

import androidx.lifecycle.LiveData
import com.zuas.fintech.zubov.topFilms.domain.model.Movie
import com.zuas.fintech.zubov.topFilms.domain.model.PaginatedMovies
import kotlinx.coroutines.flow.Flow


interface MovieRepository {

    suspend fun getMovies(page: Int): PaginatedMovies

    suspend fun getMovie(movieId: Int) : Movie

    suspend fun addMovie(movie: Movie)

    suspend fun deleteMovie(movie: Movie)

}