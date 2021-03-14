package com.jutt.moviesdetaildemo.view.fragments

import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.abed.kotlin_recycler.adapters.ViewTypeRecyclerAdapter
import com.blankj.utilcode.util.KeyboardUtils
import com.jutt.moviesdetaildemo.R
import com.jutt.moviesdetaildemo.core.BaseFragment
import com.jutt.moviesdetaildemo.data.models.Movie
import com.jutt.moviesdetaildemo.databinding.FragmentHomeBinding
import com.jutt.moviesdetaildemo.view.adapters.AdapterExtensionBindings
import com.jutt.moviesdetaildemo.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), SearchView.OnQueryTextListener {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override val titleResId: Int
        get() = R.string.app_name

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel by activityViewModels<HomeViewModel>()

    lateinit var searchMoviesAdapter: ViewTypeRecyclerAdapter<Any>

    private var searchView: SearchView? = null
    private var lastQuery: String? = null

    override fun onReady() {
        setUpViews()
        setUpObservers()

        viewModel.fetchMoviesData()
    }

    private fun setUpObservers() {
        viewModel.moviesList.observe(viewLifecycleOwner) {
            setUpSearchAdapter(it)
            KeyboardUtils.hideSoftInput(binding.root)
        }
        viewModel.searchedMovies.observe(viewLifecycleOwner) {
            setUpSearchAdapter(it)
        }
    }

    private fun setUpViews() {
        setHasOptionsMenu(true)
        setUpSimpleMoviesRecycler()
    }

    private fun setUpSearchAdapter(data: List<Any>) {
        searchMoviesAdapter =
            AdapterExtensionBindings.multiTypeBindingForSearchResults(
                binding.recyclerView,
                data,
                onClick = {
                    if (it is Movie)
                        viewModel.selectMovie(it)
                }
            )

    }

    private fun setUpSimpleMoviesRecycler() {
        setUpSearchAdapter(listOf())
        with(binding.recyclerView) {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context)
            adapter = searchMoviesAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        searchView = menu.findItem(R.id.action_search).actionView as SearchView?
        searchView?.isVisible = true
        searchView?.queryHint = getString(R.string.search_hint)
        searchView?.setIconifiedByDefault(false)
        searchView?.setOnQueryTextListener(this)

        searchView?.setOnQueryTextFocusChangeListener { _, haveFocus ->
            if(!haveFocus && lastQuery.isNullOrEmpty().not()) {
                searchView?.setQuery(lastQuery, true)
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(lastQuery != query) {
            lastQuery = query
            viewModel.searchMovies(searchQuery = lastQuery ?: "")
        }
        return false
    }

    override fun onQueryTextChange(text: String?): Boolean {
        if(lastQuery != text) {
            lastQuery = text
            viewModel.searchMovies(searchQuery = lastQuery ?: "")
        }
        return false
    }
}