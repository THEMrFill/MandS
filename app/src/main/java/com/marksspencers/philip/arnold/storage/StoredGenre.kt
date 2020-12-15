package com.marksspencers.philip.arnold.storage

import io.realm.RealmObject

open class StoredGenre(
    var id: Int = 0,
    var name: String = ""
): RealmObject()