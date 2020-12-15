package com.marksspencers.philip.arnold.model

data class StoredCollectionMovies(
    val backdrop_path: String,
    val id: Int,
    val name: String,
    val overview: String,
    val parts: List<StoredResult>,
    val poster_path: String
)