package com.marksspencers.philip.arnold.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marksspencers.philip.arnold.api.SingleLiveEvent
import com.marksspencers.philip.arnold.api.UseCaseResult
import com.marksspencers.philip.arnold.repo.FavouriteRepository
import com.marksspencers.philip.arnold.repo.GenresRepository
import com.marksspencers.philip.arnold.repo.MoviesRepository
import com.marksspencers.philip.arnold.storage.StoredGenres
import com.marksspencers.philip.arnold.model.StoredMovie
import com.marksspencers.philip.arnold.model.StoredResult
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailsViewModel(
    val moviesRepository: MoviesRepository,
    val genresRepository: GenresRepository,
    val favouriteRepository: FavouriteRepository
): ViewModel(), CoroutineScope {
    // Coroutine's background job
    private val job = Job()
    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData(true)
    val showError = SingleLiveEvent<String>()
    val listOfGenres = MutableLiveData<StoredGenres>()
    val loadedMovie = MutableLiveData<StoredResult>()
    val favouriteMovie = MutableLiveData(false)
    val movieDetails = MutableLiveData<StoredMovie>()

    var movieId: Int = 0
        set(id: Int) {
            field = id
            loadMovie()
            loadFavourites()
        }

    init {
        loadGenres()
    }

    private fun loadGenres() {
        showLoading.value = true

        launch {
            genresRepository.loadGenres(listOfGenres){}
        }
    }

    private fun loadMovie() {
        showLoading.value = true

        launch {
            when (val result = withContext(Dispatchers.IO) { moviesRepository.getMovie(movieId) }) {
                is UseCaseResult.Success -> {
                    movieDetails.value = result.data
                    checkLoading()
                }
                is UseCaseResult.Error -> {
                    showError.value = result.exception.message
                }
            }
        }
    }

    private fun loadFavourites() {
        launch {
            favouriteRepository.getFavourite(movieId, favouriteMovie) {}
        }
    }

    private fun checkLoading() {
        Pair(listOfGenres.value, loadedMovie.value).let {
            showLoading.value = false
        }
    }

    fun setFave(fave: Boolean) {
        favouriteMovie.value = fave
        launch {
            if (fave) {
                favouriteRepository.addId(movieId)
            } else {
                favouriteRepository.removeId(movieId)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }
}