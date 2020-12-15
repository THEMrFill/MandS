package com.marksspencers.philip.arnold.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marksspencers.philip.arnold.api.SingleLiveEvent
import com.marksspencers.philip.arnold.api.UseCaseResult
import com.marksspencers.philip.arnold.repo.FavouriteRepository
import com.marksspencers.philip.arnold.repo.GenresRepository
import com.marksspencers.philip.arnold.repo.MoviesRepository
import com.marksspencers.philip.arnold.storage.StoredGenres
import com.marksspencers.philip.arnold.storage.StoredIDs
import com.marksspencers.philip.arnold.model.StoredMovies
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel(
    val moviesRepository: MoviesRepository,
    val genresRepository: GenresRepository,
    val favouriteRepository: FavouriteRepository
) : ViewModel(), CoroutineScope {
    // Coroutine's background job
    private val job = Job()
    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData(true)
    val showError = SingleLiveEvent<String>()
    val listOfGenres = MutableLiveData<StoredGenres>()
    val listOfMovies = MutableLiveData<StoredMovies>()
    val favouriteList = MutableLiveData<List<Int>>()

    init {
        loadGenres()
        loadCurrentReleases()
        loadFavourites()
    }

    private fun loadGenres() {
        showLoading.value = true

        launch {
            genresRepository.loadGenres(listOfGenres){}
        }

        launch {
            when (val result = withContext(Dispatchers.IO) { genresRepository.getGenres() }) {
                is UseCaseResult.Success -> {
                    genresRepository.storeGenres(result.data)
                    listOfGenres.value = result.data
                    checkLoading()
                }
                is UseCaseResult.Error -> {
                    showError.value = result.exception.message
                }
            }
        }
    }

    private fun loadCurrentReleases() {
        showLoading.value = true

        launch {
            when (val result = withContext(Dispatchers.IO) { moviesRepository.getMovies() }) {
                is UseCaseResult.Success -> {
                    listOfMovies.value = result.data
                    checkLoading()
                }
                is UseCaseResult.Error -> {
                    showError.value = result.exception.message
                }
            }
        }
    }

    fun loadFavourites() {
        launch {
            favouriteRepository.getAll(favouriteList) {}
        }
    }

    private fun checkLoading() {
        Pair(listOfGenres.value, listOfMovies.value).let {
            showLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }
}