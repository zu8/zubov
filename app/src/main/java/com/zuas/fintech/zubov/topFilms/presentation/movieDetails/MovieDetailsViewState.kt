package com.zuas.fintech.zubov.topFilms.presentation.movieDetails

import com.zuas.fintech.zubov.topFilms.domain.model.Movie
import com.zuas.fintech.zubov.topFilms.presentation.Event

data class MovieDetailsViewState(
    val loading: Boolean = true,
    val movie: Movie = Movie(),
    val failure: Event<Throwable>? = null
)