package com.marksspencers.philip.arnold.repo

import androidx.lifecycle.MutableLiveData
import com.marksspencers.philip.arnold.api.ApiNetwork
import com.marksspencers.philip.arnold.api.UseCaseResult
import com.marksspencers.philip.arnold.storage.StoredGenres
import io.realm.Realm
import io.realm.kotlin.where

class GenresRepositoryImpl(val api: ApiNetwork): GenresRepository {
    private val realm: Realm = Realm.getDefaultInstance()

    override suspend fun getGenres(): UseCaseResult<StoredGenres> {
        return try {
            val result = api.getGenres().await()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }

    override fun storeGenres(genres: StoredGenres) {
        with(realm) {
            beginTransaction()
            // I had to delete everything, otherwise it leaves "orphaned" data
            copyToRealm(genres)
            commitTransaction()
        }
    }

    override fun loadGenres(liveData: MutableLiveData<StoredGenres>, after: () -> Unit) {
        val results = realm.where<StoredGenres>().findAll()
        liveData.value = if (results.isEmpty()) StoredGenres() else results[0]
        after()
    }
}