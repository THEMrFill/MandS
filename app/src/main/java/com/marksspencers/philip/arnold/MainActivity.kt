package com.marksspencers.philip.arnold

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marksspencers.philip.arnold.ui.collection.CollectionFragment
import com.marksspencers.philip.arnold.ui.details.DetailsFragment
import com.marksspencers.philip.arnold.ui.main.MainFragment

class MainActivity : AppCompatActivity() {
    companion object {
        const val MOVIE_DETAILS = "Movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    fun navigateToMovie(id: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailsFragment.newInstance(id))
            .addToBackStack(MOVIE_DETAILS)
            .commit()
    }

    fun navigateToCollection(id: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, CollectionFragment.newInstance(id))
            .addToBackStack(MOVIE_DETAILS)
            .commit()
    }
}