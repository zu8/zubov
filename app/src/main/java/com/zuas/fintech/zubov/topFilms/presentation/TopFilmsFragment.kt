package com.zuas.fintech.zubov.topFilms.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.zuas.fintech.zubov.R
import com.zuas.fintech.zubov.databinding.FragmentTopFilmsBinding
import com.zuas.fintech.zubov.topFilms.presentation.adapters.MovieAdapter


class TopFilmsFragment : Fragment() {

    private val viewModel: TopMoviesViewModel by viewModels()
    private var _binding: FragmentTopFilmsBinding? = null
    private val binding: FragmentTopFilmsBinding
        get() = _binding ?: throw RuntimeException("FragmentTopFilmsBinding is null")

    private var isLoading = false
    private var isLastPage = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTopFilmsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestInitialMovieList()
        setupUI()
    }

    private fun requestInitialMovieList() {
       viewModel.onEvent(TopMoviesEvent.RequestInitialList)
    }


    private fun setupUI(){
        val adapter = createAdapter()
        setupRecyclerView(adapter)
        subscribeToViewStateUpdates(adapter)
    }

    private fun subscribeToViewStateUpdates(adapter: MovieAdapter) {
        viewModel.state.observe(viewLifecycleOwner){
            updateScreenState(it,adapter)
            isLoading = it.loading
            isLastPage = it.isLastPage
        }
    }

    private fun updateScreenState(viewState: TopMoviesViewState, adapter: MovieAdapter) {
        binding.progressBar.isVisible = viewState.loading
        adapter.submitList(viewState.movies)
        handleLastPage(viewState.isLastPage)
        handleFailures(viewState.failure)

    }

    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return

        val fallbackMessage = getString(R.string.an_error_occurred)
        val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
            fallbackMessage
        }
        else {
            unhandledFailure.message!!
        }

        if (snackbarMessage.isNotEmpty()) {
            Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_LONG).show()
        }
        parentFragmentManager.popBackStack()
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.mainFragmentContainer,OnFailureFragment.newInstance())
            .commit()
    }

    private fun handleLastPage(isLastPage: Boolean) {
        //
    }

    private fun setupRecyclerView(_adapter: MovieAdapter) {
        binding.recyclerViewMovies.apply {
            adapter = _adapter
            layoutManager = LinearLayoutManager(requireContext())


        }
        binding.recyclerViewMovies
            .addOnScrollListener(createInfiniteScrollListener(
                binding.recyclerViewMovies.layoutManager as LinearLayoutManager))

    }

    private fun createAdapter(): MovieAdapter {
        return MovieAdapter(requireContext())
    }

    private fun createInfiniteScrollListener(
        layoutManager: LinearLayoutManager
    ): RecyclerView.OnScrollListener {
        return object : InfiniteScrollListener(
            layoutManager,
            TopMoviesViewModel.UI_PAGE_SIZE,
            isLoading,
            isLastPage
        ) {
            override fun loadMoreItems() { loadNextMoviePage() }
        }
    }

    private fun loadNextMoviePage() {
        viewModel.onEvent(TopMoviesEvent.RequestMoreMovies)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): Fragment {
            return TopFilmsFragment()
        }
    }
}