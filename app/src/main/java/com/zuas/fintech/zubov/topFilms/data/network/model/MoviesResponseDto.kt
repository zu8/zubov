package com.zuas.fintech.zubov.topFilms.data.network.model

import com.google.gson.annotations.SerializedName

data class MoviesResponseDto(

    @SerializedName("pagesCount")
    val pagesCount: Int,
    @SerializedName("films")
    val films: List<MovieDto>

)
