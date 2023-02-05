package com.zuas.fintech.zubov.topFilms.data.network.model

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("filmId")
    val filmId: Int,
    @SerializedName("nameRu")
    val name: String?,
    @SerializedName("year")
    val year: String?,
    @SerializedName("countries")
    val countries: List<CountryDto>,
    @SerializedName("genres")
    val genres: List<GenreDto>,
    @SerializedName("posterUrl")
    val poster: String,
    @SerializedName("posterUrlPreview")
    val posterPreview: String
)