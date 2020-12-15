package com.marksspencers.philip.arnold.repo

import com.marksspencers.philip.arnold.api.ApiNetwork
import com.marksspencers.philip.arnold.api.UseCaseResult
import com.marksspencers.philip.arnold.model.StoredCollectionMovies
import com.marksspencers.philip.arnold.model.StoredMovie
import com.marksspencers.philip.arnold.model.StoredMovies
import io.realm.Realm

class MoviesRepositoryImpl(val api: ApiNetwork): MoviesRepository {
    private val realm: Realm = Realm.getDefaultInstance()

    override suspend fun getMovies(): UseCaseResult<StoredMovies> {
        return try {
            val result = api.getNowPlaying().await()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }

    override suspend fun getMovie(id: Int): UseCaseResult<StoredMovie> {
        return try {
            val result = api.getMovie(id).await()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }

    override suspend fun getCollection(id: Int): UseCaseResult<StoredCollectionMovies> {
        return try {
            val result = api.getCollection(id).await()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }
}