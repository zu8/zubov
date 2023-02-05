package com.zuas.fintech.zubov.topFilms.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.zuas.fintech.zubov.topFilms.domain.model.Movie

object MovieDiffCallback: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}