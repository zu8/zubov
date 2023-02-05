package com.zuas.fintech.zubov.topFilms.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zuas.fintech.zubov.topFilms.data.mappers.MovieMapper
import com.zuas.fintech.zubov.topFilms.data.network.ApiFactory
import com.zuas.fintech.zubov.topFilms.data.network.ApiService
import com.zuas.fintech.zubov.topFilms.data.repositories.MovieRepositoryImpl
import com.zuas.fintech.zubov.topFilms.domain.model.PaginatedMovies
import com.zuas.fintech.zubov.topFilms.domain.usecases.GetMoviesUseCase
import kotlinx.coroutines.launch

class TopMoviesViewModel(
    application: Application,

    ) : AndroidViewModel(application) {

    private val apiService: ApiService = ApiFactory.apiService
    private val mapper = MovieMapper
    private val repository = MovieRepositoryImpl(apiService, mapper)
    private val getMoviesUseCase: GetMoviesUseCase = GetMoviesUseCase(repository)
    private var page = DEFAULT_PAGE
    private var continueDownloading = true



    private val _state = MutableLiveData<TopMoviesViewState>()
    val state: LiveData<TopMoviesViewState>
        get() = _state

    init{
        _state.value = TopMoviesViewState()
    }

    private fun isLastPage(_page: Int): Boolean {
        return _page == page
    }

    fun onEvent(event: TopMoviesEvent) {
        when (event) {
            is TopMoviesEvent.RequestInitialList -> loadMovies()
            is TopMoviesEvent.RequestMoreMovies -> loadNextMoviePage()
        }
    }

    private fun loadMovies() {
        state.value?.let {
            if (it.movies.isEmpty()) loadNextMoviePage()
        }
    }

    private fun setIsLoadingState(downloading: Boolean) {
        val newState = _state.value?.copy(loading = downloading)
        newState?.let { _state.value = it }
    }
    private fun noMoreContent(paginatedMovies: PaginatedMovies): Boolean{
        return paginatedMovies.page < 0
    }

    private fun loadNextMoviePage() {
        if (!continueDownloading) return
        setIsLoadingState(true)
        viewModelScope.launch {
            lateinit var paginatedMovies: PaginatedMovies
            try{
                paginatedMovies = getMoviesUseCase(page)
                if (noMoreContent(paginatedMovies)) {
                    setIsLoadingState(false)
                    continueDownloading = false
                    return@launch
                }
                val movieList = _state.value?.movies
                val updatedMovieSet = (movieList?.plus(paginatedMovies.movies))?.toSet()
                val newState = updatedMovieSet?.let {
                    _state.value?.copy(
                        movies = it.toList(),
                        isLastPage = isLastPage(paginatedMovies.page),
                        loading = false
                    )
                }
                _state.value = newState!!
                 page++
            }
            catch ( e: Exception){
                Log.d("MovieViewModel", e.toString())
                val newState = _state.value?.copy(
                    failure = Event(e.fillInStackTrace()),
                    loading = false
                )
                _state.value = newState!!
            }

        }
    }
    companion object{
        const val UI_PAGE_SIZE = 20
        const val DEFAULT_PAGE = 1
    }

}