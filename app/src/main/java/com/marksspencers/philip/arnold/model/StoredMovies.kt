package com.marksspencers.philip.arnold.model

import io.realm.RealmList

open class StoredMovies(
    var dates: StoredDates? = StoredDates(),
    var page: Int = 0,
    var results: RealmList<StoredResult> = RealmList(),
    var total_pages: Int = 0,
    var total_results: Int = 0
)