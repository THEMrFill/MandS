package com.marksspencers.philip.arnold.storage

import io.realm.RealmList
import io.realm.RealmObject

open class StoredGenres(
   var genres: RealmList<StoredGenre> = RealmList()
): RealmObject()