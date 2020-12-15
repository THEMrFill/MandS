package com.marksspencers.philip.arnold.repo

import androidx.lifecycle.MutableLiveData
import com.marksspencers.philip.arnold.storage.StoredIDs
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.where

class FavouriteRepositoryImpl: FavouriteRepository {
    private val realm: Realm = Realm.getDefaultInstance()

    override fun addId(id: Int) {
        val existing = getAll()
        if (!existing.ids.contains(id)) {
            val ids = RealmList<Int>().apply {
                addAll(existing.ids)
                add(id)
            }
            val results = realm.where<StoredIDs>().findAll()
            val newFaves = StoredIDs(ids)
            with(realm) {
                beginTransaction()
                results.deleteAllFromRealm()
                copyToRealm(newFaves)
                commitTransaction()
            }
        }
    }

    override fun removeId(id: Int) {
        val existing = getAll()
        if (existing.ids.contains(id)) {
            val items = RealmList<Int>().apply {
                addAll(existing.ids.filter {
                    it != id
                })
            }
            val results = realm.where<StoredIDs>().findAll()
            val newFaves = StoredIDs(items)
            with(realm) {
                beginTransaction()
                results.deleteAllFromRealm()
                copyToRealm(newFaves)
                commitTransaction()
            }
        }
    }

    override fun getAll(): StoredIDs {
        val results = realm.where<StoredIDs>().findAll()
        return if (!results.isEmpty()) {
            results[0]?.let {
                StoredIDs(it.ids)
            } ?: StoredIDs()
        } else {
            StoredIDs()
        }
    }

    override fun getAll(results: MutableLiveData<List<Int>>, unit: () -> Unit) {
        val existing = realm.where<StoredIDs>().findAll()
        if (!existing.isEmpty()) {
            existing[0]?.let { faves ->
                results.value = ArrayList<Int>().apply { addAll(faves.ids) }
            }
        }
        unit()
    }

    override fun getFavourite(id: Int, results: MutableLiveData<Boolean>, unit: () -> Unit) {
        val stored = getAll()
        val newItems = stored.ids.filter {
            it == id
        }
        results.value = !newItems.isEmpty()
        unit()
    }
}