package com.zuas.fintech.zubov.topFilms.domain.usecases

import com.zuas.fintech.zubov.topFilms.domain.repositories.MovieRepository

class LoadSingleMovieUseCase(private val movieRepo: MovieRepository) {

    suspend operator fun invoke(filmId: Int) = movieRepo.loadSingleMovie(filmId)


}