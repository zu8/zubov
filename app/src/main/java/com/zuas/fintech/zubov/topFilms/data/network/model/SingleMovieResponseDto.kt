package com.zuas.fintech.zubov.topFilms.data.network.model

import com.google.gson.annotations.SerializedName

data class SingleMovieResponseDto(
    @SerializedName("kinopoiskId")
    val filmId: Int,
    @SerializedName("nameRu")
    val name: String?,
    @SerializedName("posterUrl")
    val poster: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("countries")
    val countries: List<CountryDto>,
    @SerializedName("genres")
    val genres: List<GenreDto>
)
