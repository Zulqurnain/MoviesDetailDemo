package com.jutt.moviesdetaildemo.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jutt.moviesdetaildemo.R
import com.jutt.moviesdetaildemo.architecture.EventObserver
import com.jutt.moviesdetaildemo.core.BaseFragment
import com.jutt.moviesdetaildemo.databinding.FragmentHomeBinding
import com.jutt.moviesdetaildemo.databinding.FragmentHomeDetailsBinding
import com.jutt.moviesdetaildemo.view.adapters.MoviesListAdapter
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
        viewModel.selectedMovie.observe(viewLifecycleOwner,EventObserver{
            binding.textView.text = it.toString()
        })
    }

    private fun setUpViews() {
    }

}