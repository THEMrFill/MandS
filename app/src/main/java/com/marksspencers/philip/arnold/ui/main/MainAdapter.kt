package com.marksspencers.philip.arnold.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marksspencers.philip.arnold.R
import com.marksspencers.philip.arnold.databinding.MovieItemBinding
import com.marksspencers.philip.arnold.storage.StoredIDs
import com.marksspencers.philip.arnold.model.StoredMovies
import com.marksspencers.philip.arnold.model.StoredResult
import com.marksspencers.philip.arnold.utils.formatDate

class MainAdapter(private val click: (id: Int) -> Unit): RecyclerView.Adapter<MovieViewHolder>() {
    private var displayMovies = StoredMovies()
    private var favourites: List<Int> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, click)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        displayMovies.results[position]?.let {
            holder.bind(it, favourites.contains(it.id))
        }
    }

    override fun getItemCount(): Int = displayMovies.results.size

    fun setData(movies: StoredMovies) {
        displayMovies = movies
        notifyDataSetChanged()
    }
    fun setFavourites(ids: List<Int>) {
        favourites = ids
    }
}

class MovieViewHolder(private val binder: MovieItemBinding, private val click: (id: Int) -> Unit):
    RecyclerView.ViewHolder(binder.root) {

    fun bind(movie: StoredResult, favourite: Boolean) {
        with (binder) {
            title.text = movie.title
            releaseDate.text = movie.release_date.formatDate()
            star.setImageResource(
                if (favourite)
                    R.drawable.ic_baseline_star_24
                else
                    R.drawable.ic_baseline_star_outline_24
            )
            binder.root.setOnClickListener {
                click(movie.id)
            }
        }
    }
}
