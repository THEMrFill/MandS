package com.marksspencers.philip.arnold.repo

import androidx.lifecycle.MutableLiveData
import com.marksspencers.philip.arnold.api.UseCaseResult
import com.marksspencers.philip.arnold.model.StoredMovie
import com.marksspencers.philip.arnold.model.StoredMovies
import com.marksspencers.philip.arnold.model.StoredResult
import com.marksspencers.philip.arnold.model.StoredCollectionMovies

interface MoviesRepository {
    suspend fun getMovies(): UseCaseResult<StoredMovies>
    suspend fun getMovie(id: Int): UseCaseResult<StoredMovie>
    suspend fun getCollection(id: Int): UseCaseResult<StoredCollectionMovies>
}