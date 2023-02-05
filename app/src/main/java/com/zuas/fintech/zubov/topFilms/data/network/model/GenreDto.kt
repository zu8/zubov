package com.zuas.fintech.zubov.topFilms.data.network.model

import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("genre")
    val genre: String
)
