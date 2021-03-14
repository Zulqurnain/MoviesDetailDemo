package com.jutt.moviesdetaildemo.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jutt.moviesdetaildemo.R
import com.jutt.moviesdetaildemo.architecture.EventObserver
import com.jutt.moviesdetaildemo.core.AppNavigationActivity
import com.jutt.moviesdetaildemo.core.AppSupportActivity
import com.jutt.moviesdetaildemo.core.BaseFragment
import com.jutt.moviesdetaildemo.databinding.FragmentHomeBinding
import com.jutt.moviesdetaildemo.databinding.FragmentHomeDetailsBinding
import com.jutt.moviesdetaildemo.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDetailsFragment : BaseFragment<FragmentHomeDetailsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeDetailsBinding
        get() = FragmentHomeDetailsBinding::inflate

    override val titleResId: Int
        get() = R.string.app_name

    companion object {
        fun newInstance() = HomeDetailsFragment()
    }

    private val viewModel by activityViewModels<HomeViewModel>()

    override fun onReady() {
        setUpObservers()
        setUpViews()
    }

    private fun setUpObservers() {
        viewModel.selectedMovie.observe(viewLifecycleOwner){
            binding.textView.text = it.toString()
        }
    }

    override fun onNavigateBack(): Boolean {
        val actionBar = (activity as AppNavigationActivity).supportActionBar ?: return super.onNavigateBack()
        actionBar.setDisplayHomeAsUpEnabled(false)
        return super.onNavigateBack()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val searchView: SearchView? = menu.findItem(R.id.action_search).actionView as SearchView?
        searchView?.isVisible = false
    }

    private fun setUpViews() {
    }

}