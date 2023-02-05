package com.zuas.fintech.zubov.topFilms.presentation.movieDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zuas.fintech.zubov.topFilms.data.mappers.MovieMapper
import com.zuas.fintech.zubov.topFilms.data.network.ApiFactory
import com.zuas.fintech.zubov.topFilms.data.network.ApiService
import com.zuas.fintech.zubov.topFilms.data.repositories.MovieRepositoryImpl
import com.zuas.fintech.zubov.topFilms.domain.model.Movie
import com.zuas.fintech.zubov.topFilms.domain.usecases.LoadSingleMovieUseCase
import com.zuas.fintech.zubov.topFilms.presentation.Event
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    application: Application,
    itemId: Int
) : AndroidViewModel(application) {

    private val apiService: ApiService = ApiFactory.apiService
    private val mapper = MovieMapper
    private val repository = MovieRepositoryImpl(apiService, mapper)
    private val loadSingleMovieUseCase: LoadSingleMovieUseCase = LoadSingleMovieUseCase(repository)
    private val id: Int = itemId
    private val _state = MutableLiveData<MovieDetailsViewState>()
    val state: LiveData<MovieDetailsViewState>
        get() = _state

    init {
        _state.value = MovieDetailsViewState()
    }

    fun onEvent(event: MovieDetailsEvent) {
        when (event) {
            is MovieDetailsEvent.RequestFilm -> loadMovie()
        }
    }

    private fun setIsLoadingState(downloading: Boolean) {
        val newState = _state.value?.copy(loading = downloading)
        newState?.let { _state.value = it }
    }

    private fun loadMovie() {
        setIsLoadingState(true)
        viewModelScope.launch {
            lateinit var loadedMovie: Movie
            try {
                loadedMovie = loadSingleMovieUseCase(id)
                val newState = _state.value?.copy(
                    movie = loadedMovie,
                    loading = false
                )
                _state.value = newState!!
            } catch (e: Exception) {
                val newState = _state.value?.copy(
                    failure = Event(e.fillInStackTrace()),
                    loading = false
                )
                _state.value = newState!!
            }
        }
    }

}