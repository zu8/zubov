package com.zuas.fintech.zubov.topFilms.domain.model

data class PaginatedMovies(
    val page: Int,
    val movies: List<Movie>
)