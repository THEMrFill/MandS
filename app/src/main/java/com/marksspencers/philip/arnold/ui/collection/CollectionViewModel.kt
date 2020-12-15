package com.marksspencers.philip.arnold.ui.collection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marksspencers.philip.arnold.api.SingleLiveEvent
import com.marksspencers.philip.arnold.api.UseCaseResult
import com.marksspencers.philip.arnold.repo.MoviesRepository
import com.marksspencers.philip.arnold.model.StoredCollectionMovies
import com.marksspencers.philip.arnold.repo.FavouriteRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CollectionViewModel(
    val moviesRepository: MoviesRepository,
    val favouriteRepository: FavouriteRepository
): ViewModel(), CoroutineScope {
    // Coroutine's background job
    private val job = Job()
    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData(true)
    val showError = SingleLiveEvent<String>()
    val collection = MutableLiveData<StoredCollectionMovies>()
    val favouriteList = MutableLiveData<List<Int>>()

    var collectionId: Int = 0
        set(id: Int) {
            field = id
            loadCollection()
        }

    init {
        loadFavourites()
    }

    private fun loadCollection() {
        showLoading.value = true

        launch {
            when (val result = withContext(Dispatchers.IO) { moviesRepository.getCollection(collectionId) }) {
                is UseCaseResult.Success -> {
                    collection.value = result.data
                    showLoading.value = false
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
}