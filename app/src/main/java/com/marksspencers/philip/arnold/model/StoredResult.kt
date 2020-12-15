package com.marksspencers.philip.arnold.model

import io.realm.RealmList
import io.realm.RealmObject

open class StoredResult(
    var adult: Boolean = false,
    var genre_ids: RealmList<Int> = RealmList(),
    var id: Int = 0,
    var original_language: String = "",
    var original_title: String = "",
    var overview: String = "",
    var poster_path: String = "",
    var release_date: String = "",
    var title: String = "",
)