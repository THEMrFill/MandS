package com.marksspencers.philip.arnold.repo

import androidx.lifecycle.MutableLiveData
import com.marksspencers.philip.arnold.api.UseCaseResult
import com.marksspencers.philip.arnold.storage.StoredGenres

interface GenresRepository {
    suspend fun getGenres(): UseCaseResult<StoredGenres>
    fun storeGenres(genres: StoredGenres)
    fun loadGenres(liveData: MutableLiveData<StoredGenres>, after: () -> Unit)
}