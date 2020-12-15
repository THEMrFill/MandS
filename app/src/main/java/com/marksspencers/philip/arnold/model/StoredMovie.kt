package com.marksspencers.philip.arnold.model

import com.marksspencers.philip.arnold.storage.StoredGenre

data class StoredMovie(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: StoredCollection? = null,
    val budget: Int,
    val genres: List<StoredGenre>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<Any>,
    val production_countries: List<Any>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<Any>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)