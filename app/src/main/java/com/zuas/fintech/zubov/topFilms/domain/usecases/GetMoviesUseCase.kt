package com.zuas.fintech.zubov.topFilms.domain.usecases

import com.zuas.fintech.zubov.topFilms.domain.repositories.MovieRepository

class GetMoviesUseCase(private val movieRepo: MovieRepository) {
    suspend operator fun invoke(page: Int) = movieRepo.getMovies(page)
}