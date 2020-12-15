package com.marksspencers.philip.arnold.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.marksspencers.philip.arnold.MainActivity
import com.marksspencers.philip.arnold.R
import com.marksspencers.philip.arnold.databinding.MainFragmentBinding
import com.marksspencers.philip.arnold.utils.nonNullObserve
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: MainFragmentBinding
    private val mainAdapter = MainAdapter(::clickThrough)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)

        with (binding) {
            with (recycler) {
                adapter = mainAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavourites()
    }

    private fun setupObservers() {
        with (viewModel) {
            listOfMovies.nonNullObserve(viewLifecycleOwner) { movies ->
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