package com.jutt.citiessearchmvvm.view.fragments

import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.KeyboardUtils
import com.jutt.citiessearchmvvm.R
import com.jutt.citiessearchmvvm.core.BaseFragment
import com.jutt.citiessearchmvvm.data.models.GeoCity
import com.jutt.citiessearchmvvm.databinding.FragmentHomeBinding
import com.jutt.citiessearchmvvm.extensions.value
import com.jutt.citiessearchmvvm.view.adapters.CitiesListAdapter
import com.jutt.citiessearchmvvm.view.decorations.SpaceItemDecoration
import com.jutt.citiessearchmvvm.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(){
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    companion object {
        fun newInstance() = HomeFragment()
    }

    override val titleResId: Int
        get() = R.string.app_name

    private val citiesAdapter by lazy { CitiesListAdapter() }

    private val viewModel by activityViewModels<HomeViewModel>()

    override fun onReady() {
        setUpViews()
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.searchedCities.observe(viewLifecycleOwner) {
            setUpSearchAdapter(it)
        }
    }

    private fun setUpViews() {
        with(binding.recyclerView){
            layoutManager = LinearLayoutManager(context)
            this.addItemDecoration(
                SpaceItemDecoration(
                    space = context.resources.getDimensionPixelSize(R.dimen.margin_design_20),
                    spaceBorderMultiplier = 1
                )
            )
            adapter = citiesAdapter
        }

        binding.btnSearch.setOnClickListener {
            viewModel.searchMovies(
                searchQuery = binding.etSearch.value
            )
            KeyboardUtils.hideSoftInput(binding.root)
        }

    }

    private fun setUpSearchAdapter(data: List<GeoCity>) {
        citiesAdapter.submitList(data)
        citiesAdapter.notifyDataSetChanged()
    }

}