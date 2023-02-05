package com.zuas.fintech.zubov.topFilms.domain.usecases

import com.zuas.fintech.zubov.topFilms.domain.repositories.MovieRepository

class LoadMoviesUseCase(private val movieRepo: MovieRepository) {
    suspend operator fun invoke(page: Int) = movieRepo.loadMovies(page)
}