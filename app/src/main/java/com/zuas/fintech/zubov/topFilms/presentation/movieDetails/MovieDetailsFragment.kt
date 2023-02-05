package com.zuas.fintech.zubov.topFilms.presentation.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.zuas.fintech.zubov.R
import com.zuas.fintech.zubov.databinding.FragmentMovieDetailsBinding
import com.zuas.fintech.zubov.topFilms.domain.model.Country
import com.zuas.fintech.zubov.topFilms.domain.model.Genre
import com.zuas.fintech.zubov.topFilms.presentation.Event
import com.zuas.fintech.zubov.topFilms.presentation.OnFailureFragment

class MovieDetailsFragment : Fragment() {

    lateinit var viewModel: MovieDetailsViewModel
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding: FragmentMovieDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentMovieDetailsBinding is null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = getIdFromArgs()
        val factory = MovieDetailsViewModelFactory(requireActivity().application, movieId)
        viewModel = ViewModelProvider(this, factory)[MovieDetailsViewModel::class.java]
        requestMovie()
        subscribeToViewStateUpdates()
    }

    private fun getIdFromArgs(): Int {
        return requireArguments().getInt(EXTRA_ID, 0)
    }

    private fun requestMovie() {
        viewModel.onEvent(MovieDetailsEvent.RequestFilm)
    }

    private fun subscribeToViewStateUpdates() {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState(it)
        }
    }

    private fun updateScreenState(viewState: MovieDetailsViewState) {
        val movie = viewState.movie
        getCountryString(movie.countries)
        binding.progressBar.isVisible = viewState.loading
        binding.genresLabel.isVisible = !viewState.loading
        binding.countriesLabel.isVisible = !viewState.loading
        binding.textViewTitle.text = movie.name
        binding.textViewDescription.text = movie.description
        binding.countries.text = getCountryString(movie.countries)
        binding.genres.text = getGenresString(movie.genres)
        if (movie.poster.isNotEmpty()) Picasso.get().load(movie.poster)
            .into(binding.imageViewPoster)
        handleFailures(viewState.failure)
    }

    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return

        val fallbackMessage = getString(R.string.an_error_occurred)
        val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
            fallbackMessage
        } else {
            unhandledFailure.message!!
        }

        if (snackbarMessage.isNotEmpty()) {
            Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_LONG).show()
        }

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.mainFragmentContainer, OnFailureFragment.newInstance())
            .commit()
    }

    private fun getGenresString(genres: List<Genre>): String {
        val result = mutableListOf<String>()
        for (genre in genres) {
            result.add(genre.genre)
        }
        return result.joinToString(", ")
    }

    private fun getCountryString(countries: List<Country>): String {
        val result = mutableListOf<String>()
        for (country in countries) {
            result.add(country.country)
        }
        return result.joinToString(", ")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val EXTRA_ID = "id"

        fun newInstance(movieId: Int): MovieDetailsFragment {
            return MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_ID, movieId)
                }
            }
        }
    }

}