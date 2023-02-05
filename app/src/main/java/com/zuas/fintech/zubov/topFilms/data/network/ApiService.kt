package com.zuas.fintech.zubov.topFilms.data.network

import com.zuas.fintech.zubov.topFilms.data.network.model.MoviesResponseDto
import com.zuas.fintech.zubov.topFilms.data.network.model.SingleMovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

     @GET("api/v2.2/films/top")
     suspend fun loadTopMovies(@Query("type") type: String = "TOP_100_POPULAR_FILMS",@Query("page") page: Int
     ): Response<MoviesResponseDto>

     @GET("api/v2.2/films/{id}")
     suspend fun loadMovieDetails(@Path("id") movieId: Int): Response<SingleMovieResponseDto>

}