package com.zuas.fintech.zubov.topFilms.presentation.movieDetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MovieDetailsViewModelFactory(
    private val application: Application,
    private val id: Int = 0
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(application = application, itemId = id) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}