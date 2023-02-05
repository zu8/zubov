package com.zuas.fintech.zubov.topFilms.presentation.topMovies

sealed class TopMoviesEvent {
    object RequestInitialList : TopMoviesEvent()
    object RequestMoreMovies : TopMoviesEvent()
}
