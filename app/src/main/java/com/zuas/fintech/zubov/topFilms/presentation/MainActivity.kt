package com.zuas.fintech.zubov.topFilms.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zuas.fintech.zubov.R
import com.zuas.fintech.zubov.databinding.ActivityMainBinding
import com.zuas.fintech.zubov.topFilms.presentation.topMovies.TopFilmsFragment

class MainActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            launchMovieFragment()
        }
    }

    private fun launchMovieFragment() {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFragmentContainer, TopFilmsFragment.newInstance())
            .commit()
    }
}