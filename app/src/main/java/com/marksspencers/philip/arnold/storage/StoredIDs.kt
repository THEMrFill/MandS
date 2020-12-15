package com.marksspencers.philip.arnold.storage

import io.realm.RealmList
import io.realm.RealmObject

open class StoredIDs(
    var ids: RealmList<Int> = RealmList()
): RealmObject()