package com.marksspencers.philip.arnold.repo

import androidx.lifecycle.MutableLiveData
import com.marksspencers.philip.arnold.storage.StoredIDs

interface FavouriteRepository {
    fun addId(id: Int)
    fun removeId(id: Int)
    fun getAll(): StoredIDs
    fun getAll(results: MutableLiveData<List<Int>>, unit: () -> Unit)
    fun getFavourite(id: Int, results: MutableLiveData<Boolean>, unit: () -> Unit)
}