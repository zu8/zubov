package com.zuas.fintech.zubov.topFilms.data.mappers

import com.zuas.fintech.zubov.topFilms.data.network.model.CountryDto
import com.zuas.fintech.zubov.topFilms.data.network.model.GenreDto
import com.zuas.fintech.zubov.topFilms.data.network.model.MovieDto
import com.zuas.fintech.zubov.topFilms.data.network.model.SingleMovieResponseDto
import com.zuas.fintech.zubov.topFilms.domain.model.Country
import com.zuas.fintech.zubov.topFilms.domain.model.Genre
import com.zuas.fintech.zubov.topFilms.domain.model.Movie
import com.zuas.fintech.zubov.topFilms.domain.model.PaginatedMovies

object MovieMapper {

    private fun mapCountryDtoListToCountryList(countriesDto: List<CountryDto>): List<Country> {
        val list = mutableListOf<Country>()
        for (countryDto in countriesDto) {
            list.add(
                Country(
                    country = countryDto.country
                )
            )
        }
        return list.toList()
    }

    private fun mapGenreDtoListToGenreList(genresDto: List<GenreDto>): List<Genre> {
        val list = mutableListOf<Genre>()
        for (genreDto in genresDto) {
            list.add(
                Genre(
                    genre = genreDto.genre
                )
            )
        }
        return list.toList()
    }


    fun mapMovieDtoToMovie(movieDto: MovieDto): Movie {
        return Movie(
            movieId = movieDto.filmId,
            name = movieDto.name,
            year = movieDto.year,
            countries = mapCountryDtoListToCountryList(movieDto.countries),
            genres = mapGenreDtoListToGenreList(movieDto.genres),
            poster = movieDto.poster,
            posterPreview = movieDto.posterPreview
        )
    }

    fun mapMovieDtoListToMovieList(moviesDto: List<MovieDto>): List<Movie> {
        val list = mutableListOf<Movie>()
        for (movieDto in moviesDto) {
            list.add(mapMovieDtoToMovie(movieDto))
        }
        return list.toList()
    }

    fun mapMoviesResponseDtoToPaginatedMovies(
        page: Int,
        movieDtoList: List<MovieDto>
    ): PaginatedMovies {
        return PaginatedMovies(
            page = page,
            movies = mapMovieDtoListToMovieList(movieDtoList)
        )
    }

    fun mapSingleMovieDtoToMovie(singleMovieResponseDto: SingleMovieResponseDto): Movie {
        return Movie(
            movieId = singleMovieResponseDto.filmId,
            name = singleMovieResponseDto.name,
            year = "",
            countries = mapCountryDtoListToCountryList(singleMovieResponseDto.countries),
            genres = mapGenreDtoListToGenreList(singleMovieResponseDto.genres),
            poster = singleMovieResponseDto.poster,
            description = singleMovieResponseDto.description ?: NO_DESCRIPTION
        )

    }

    const val NO_DESCRIPTION = "К сожалению, описание для этого фильма отсутствует"
}