package com.marksspencers.philip.arnold.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.marksspencers.philip.arnold.BuildConfig
import com.marksspencers.philip.arnold.MainActivity
import com.marksspencers.philip.arnold.R
import com.marksspencers.philip.arnold.databinding.DetailsFragmentBinding
import com.marksspencers.philip.arnold.model.StoredCollection
import com.marksspencers.philip.arnold.model.StoredResult
import com.marksspencers.philip.arnold.utils.formatDate
import com.marksspencers.philip.arnold.utils.nonNullObserve
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsFragment: Fragment() {
    companion object {
        const val ID = "ID"

        fun newInstance(id: Int): DetailsFragment {
            val bundle = Bundle()
            bundle.putInt(ID, id)
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel: DetailsViewModel by viewModel()
    private lateinit var binding: DetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DetailsFragmentBinding.bind(view)

        with (binding) {
            star.setOnClickListener { _ ->
                with (viewModel) {
                    setFave(favouriteMovie.value != true)
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = arguments
        bundle?.let {
            viewModel.movieId = bundle.getInt(ID, 0)
        } ?: run {

        }
        setupObservers()
    }

    private fun setupObservers() {
        with (viewModel) {
            loadedMovie.nonNullObserve(viewLifecycleOwner) { movie ->
                if (movie.title.isNotEmpty())
                    setupDisplay(movie)
            }
            favouriteMovie.nonNullObserve(viewLifecycleOwner) {
                binding.star.setImageResource(
                    if (it)
                        R.drawable.ic_baseline_star_24
                    else
                        R.drawable.ic_baseline_star_outline_24
                )
            }
            movieDetails.nonNullObserve(viewLifecycleOwner) { movie ->
                val newMovie = StoredResult(
                    title = movie.title,
                    release_date = movie.release_date,
                    overview = movie.overview,
                    poster_path = movie.poster_path,
                )
                setupDisplay(newMovie)

                val builder = StringBuilder()
                for (item in movie.genres) {
                    builder.append(item.name).append("\n")
                }
                with (binding) {
                    genresTitle.isVisible = true
                    genres.isVisible = true
                    genres.text = builder.toString().dropLast(1)
                }

                val movieCollection = movie.belongs_to_collection
                // NOTE: this is here to "force" a collections link available on the details page,
                // just in case there are no collections in the "now playing", allowing display
                // of the Lord of the Rings collection, because they're great! 😊
//                val movieCollection = StoredCollection(
//                    id = 119,
//                    name= "The Lord of the Rings Collection",
//                    poster_path= "/p4UokEk2XnjjRTdXGe6DLYXlbI1.jpg",
//                    backdrop_path= "/bccR2CGTWVVSZAG0yqmy3DIvhTX.jpg"
//                )
                if (movieCollection?.name?.isNotBlank() == true) {
                    with (binding) {
                        collectionTitle.isVisible = true
                        with (collection) {
                            isVisible = true
                            text = movieCollection.name

                            setOnClickListener {
                                navigateToCollection(movieCollection.id)
                            }
                        }
                    }
                }
            }

            showLoading.nonNullObserve(viewLifecycleOwner) { showLoading ->
                with (binding.spinner) {
                    if (showLoading) {
                        show()
                    } else {
                        hide()
                    }
                }
            }
        }
    }

    fun setupDisplay(movie: StoredResult) {
        with (binding) {
            title.text = movie.title
            releaseDate.text = movie.release_date.formatDate()
            description.text = movie.overview

            val url = BuildConfig.IMAGE_BASE_URL + movie.poster_path
            Glide.with(poster)
                .load(url)
                .into(poster)
        }
    }

    fun navigateToCollection(id: Int) {
        (requireActivity() as MainActivity).navigateToCollection(id)
    }
}