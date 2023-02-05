package com.zuas.fintech.zubov.topFilms.presentation

sealed class TopMoviesEvent{
    object RequestInitialList: TopMoviesEvent()
    object RequestMoreMovies: TopMoviesEvent()
}
