package com.zuas.fintech.zubov.topFilms.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import com.zuas.fintech.zubov.R
import com.zuas.fintech.zubov.databinding.MovieItemBinding
import com.zuas.fintech.zubov.topFilms.domain.model.Movie

class MovieAdapter(
    private val context: Context
) : ListAdapter<Movie, MovieViewHolder>(MovieDiffCallback) {

    var onMovieClickListener: OnMovieClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        with(holder.binding) {
            val genresTemplate = context.getString(R.string.genre_year_template)
            textViewGenre.text = String.format(genresTemplate, movie.genres[0].genre, movie.year)
            textViewName.text = movie.name
            Picasso.get().load(movie.posterPreview).into(imageViewPosterPreview)
            root.setOnClickListener{
                onMovieClickListener?.onMovieClick(movie)
            }
            star.setOnClickListener {
                onMovieClickListener?.onStarClick(movie)
            }
        }

    }

    interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
        fun onStarClick(movie: Movie)
    }
}