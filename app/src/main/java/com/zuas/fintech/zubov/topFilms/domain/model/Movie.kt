package com.zuas.fintech.zubov.topFilms.domain.model

data class Movie(
    val movieId : Int,
    val name: String?,
    val year: String?,
    val countries: List<Country>,
    val genres: List<Genre>,
    val poster: String,
    val posterPreview: String,
    var description: String = ""
)
