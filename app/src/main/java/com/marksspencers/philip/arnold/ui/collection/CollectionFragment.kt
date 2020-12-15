package com.marksspencers.philip.arnold.ui.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.marksspencers.philip.arnold.MainActivity
import com.marksspencers.philip.arnold.R
import com.marksspencers.philip.arnold.databinding.CollectionFragmentBinding
import com.marksspencers.philip.arnold.model.StoredMovies
import com.marksspencers.philip.arnold.ui.main.MainAdapter
import com.marksspencers.philip.arnold.utils.nonNullObserve
import org.koin.android.viewmodel.ext.android.viewModel

class CollectionFragment: Fragment() {
    companion object {
        const val ID = "ID"

        fun newInstance(id: Int): CollectionFragment {
            val bundle = Bundle()
            bundle.putInt(ID, id)
            val fragment = CollectionFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel: CollectionViewModel by viewModel()
    private lateinit var binding: CollectionFragmentBinding
    private val mainAdapter = MainAdapter(::clickThrough)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.collection_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = CollectionFragmentBinding.bind(view)

        with (binding) {
            with (recycler) {
                adapter = mainAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = arguments
        bundle?.let {
            viewModel.collectionId = bundle.getInt(ID, 0)
        } ?: run {

        }

        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavourites()
    }

    private fun setupObservers() {
        with(viewModel) {
            collection.nonNullObserve(viewLifecycleOwner) { collection ->
                with (binding) {
                    title.text = collection.name
                    overview.text = collection.overview
                }
                val movies = StoredMovies().apply {
                    results.addAll(collection.parts)
                }
                mainAdapter.setData(movies)
            }
            favouriteList.nonNullObserve(viewLifecycleOwner) { favourites ->
                mainAdapter.setFavourites(favourites)
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


    fun clickThrough(id: Int) {
        (requireActivity() as MainActivity).navigateToMovie(id)
    }
}