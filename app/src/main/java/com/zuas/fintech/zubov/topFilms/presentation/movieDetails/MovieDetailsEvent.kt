package com.zuas.fintech.zubov.topFilms.presentation.movieDetails

sealed class MovieDetailsEvent {
    object RequestFilm : MovieDetailsEvent()
}
