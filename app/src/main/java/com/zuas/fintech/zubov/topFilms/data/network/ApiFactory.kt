package com.zuas.fintech.zubov.topFilms.data.network


import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {

    private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
    private const val API_KEY = "b94d4475-596d-4c7c-a90b-74dbcb0992f5"
    private const val API_KEY2 = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"


    private val retrofit by lazy {

        val interceptor: Interceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest = chain.request()
                val newRequest = originalRequest
                    .newBuilder()
                    .addHeader("X-API-KEY", API_KEY2)
                    .build()
                return chain.proceed(newRequest)
            }
        }


        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val myClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(interceptor)
            .addInterceptor(logging)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(myClient)
            .build()
    }

    val apiService = retrofit.create(ApiService::class.java)
}
//.retryOnConnectionFailure(true)