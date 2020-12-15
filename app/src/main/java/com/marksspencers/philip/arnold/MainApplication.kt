package com.marksspencers.philip.arnold

import androidx.multidex.MultiDexApplication
import com.marksspencers.philip.arnold.di.appModules
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmMigration
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin

class MainApplication: MultiDexApplication(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        startKoin {
            androidContext(this@MainApplication)
            modules(appModules)
        }
    }
}