package com.marksspencers.philip.arnold.api

import com.marksspencers.philip.arnold.BuildConfig
import com.marksspencers.philip.arnold.model.StoredCollectionMovies
import com.marksspencers.philip.arnold.storage.StoredGenres
import com.marksspencers.philip.arnold.model.StoredMovie
import com.marksspencers.philip.arnold.model.StoredMovies
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiNetwork {
    @GET("movie/now_playing")
    fun getNowPlaying(
        @Query("region") region: String = "gb",
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): Deferred<StoredMovies>

    @GET("movie/{id}")
    fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): Deferred<StoredMovie>

    @GET("collection/{id}")
    fun getCollection(
        @Path("id") id: Int,
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): Deferred<StoredCollectionMovies>

    @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): Deferred<StoredGenres>
}