package com.zuas.fintech.zubov.topFilms.presentation.topMovies

import com.zuas.fintech.zubov.topFilms.domain.model.Movie
import com.zuas.fintech.zubov.topFilms.presentation.Event

data class TopMoviesViewState(
    val loading: Boolean = true,
    val movies: List<Movie> = emptyList(),
    val isLastPage: Boolean = false,
    val failure: Event<Throwable>? = null
)
