package com.marksspencers.philip.arnold.model

import io.realm.RealmObject

open class StoredCollection(
    var id: Int = 0,
    var name: String = "",
    var poster_path: String = "",
    var backdrop_path: String = "",
)