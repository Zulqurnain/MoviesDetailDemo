package com.jutt.moviesdetaildemo.view.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jutt.moviesdetaildemo.R
import com.jutt.moviesdetaildemo.core.BaseFragment
import com.jutt.moviesdetaildemo.core.RecyclerViewAdapter
import com.jutt.moviesdetaildemo.data.models.Movie
import com.jutt.moviesdetaildemo.databinding.FragmentHomeBinding
import com.jutt.moviesdetaildemo.view.adapters.MoviesListAdapter
import com.jutt.moviesdetaildemo.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override val titleResId: Int
        get() = R.string.app_name

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel by activityViewModels<HomeViewModel>()

    private val adapterMovies: MoviesListAdapter by lazy { MoviesListAdapter(requireContext())}

    override fun onReady() {
        setUpObservers()
        setUpViews()

        viewModel.syncMoviesFromLocal()
    }

    private fun setUpObservers() {
        viewModel.moviesList.observe(viewLifecycleOwner){
            adapterMovies.set(it.toMutableList())
        }
    }

    private fun setUpViews() {
        setUpMoviesRecycler()
    }

    private fun setUpMoviesRecycler(){
        with(binding.recyclerView) {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context)
            adapter = adapterMovies
        }

        adapterMovies.setOnItemClickListener(
            onItemClick = object: RecyclerViewAdapter.OnItemClickListener<Movie>{
                override fun onItemClick(
                    adapter: RecyclerViewAdapter<Movie, *>,
                    view: View,
                    position: Int,
                    item: Movie
                ) {
                    viewModel.navigateToHomeDetails(item)
                }
            }
        )
    }
}