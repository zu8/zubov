package com.zuas.fintech.zubov.topFilms.domain.model

data class Movie(
    val movieId : Int = 0,
    val name: String? = DEFAULT_VALUE,
    val year: String? = DEFAULT_VALUE,
    val countries: List<Country> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val poster: String = DEFAULT_VALUE,
    val posterPreview: String = DEFAULT_VALUE,
    var description: String = DEFAULT_VALUE
){
    companion object {
        const val DEFAULT_VALUE = ""

    }
}

