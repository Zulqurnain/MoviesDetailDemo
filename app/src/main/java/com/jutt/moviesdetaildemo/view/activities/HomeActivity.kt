package com.jutt.moviesdetaildemo.view.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.jutt.moviesdetaildemo.R
import com.jutt.moviesdetaildemo.architecture.*
import com.jutt.moviesdetaildemo.core.BaseNavigationActivity
import com.jutt.moviesdetaildemo.databinding.ActivityHomeBinding
import com.jutt.moviesdetaildemo.utils.loadImageFromDrawable
import com.jutt.moviesdetaildemo.view.fragments.HomeDetailsFragment
import com.jutt.moviesdetaildemo.view.fragments.HomeFragment
import com.jutt.moviesdetaildemo.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseNavigationActivity<ActivityHomeBinding>() {

    private val viewModel by viewModels<HomeViewModel>()

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, HomeActivity::class.java)
    }

    override fun onReady() {
        setupViews()
        setupObservers()
        setupHome()
    }


    private fun setupHome() {
        viewModel.start()
    }

    private fun setupViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.toolbarView.toolbar.transitionName = getString(R.string.transition_appLogo)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            supportPostponeEnterTransition()
        }

        binding.progressBarIv.loadImageFromDrawable(drawable = R.drawable.loading)
    }

    private fun setupObservers() {
        viewModel.toolbarVisible.observe(this){
            binding.toolbarView.toolbar.isVisible = it
        }

        viewModel.showLoader.observe(this, LoaderObserver(progressView = binding.progressBar))

        viewModel.successMessage.observe(this, EventObserver { message ->
            runOnUiThread {
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.popupMessage.observe(this, ErrorMessageObserver(activity = this))

        viewModel.navigate.observe(this, NavigationObserver(listener = this))

        viewModel.selectedMovie.observe(this){
            viewModel.navigateToHomeDetails()
        }
    }

    override fun onBackPressed() {
        if (viewModel.showLoader.value != true) {
            super.onBackPressed()
        }
    }

    override fun handleNonFragmentNavigationInstance(event: String) {
        super.isFragmentNavigation(event)
    }

    override fun getFragmentNavigationInstance(event: String): NavigationInstance {
        return when (event) {
            HomeViewModel.Events.NAVIGATE_TO_HOME -> {
                setUpToolBar(
                    toolbarBinding = binding.toolbarView
                )
                return NavigationInstance(
                    fragment = HomeFragment.newInstance(),
                    addToBackStack = false,
                    pushAnimation = NavigationAnimation.FADE_IN
                )
            }
            HomeViewModel.Events.NAVIGATE_TO_HOME_DETAILS -> {
                val isLandScape = binding.inContent.contentDetail != null
                setUpToolBar(
                    toolbarBinding = binding.toolbarView,
                    showBackArrowOrHome = isLandScape.not()
                )
                return NavigationInstance(
                    frameId = if(isLandScape) R.id.contentDetail else R.id.content,
                    fragment = HomeDetailsFragment.newInstance(),
                    pushAnimation = NavigationAnimation.FADE_IN
                )
            }
            else -> super.getFragmentNavigationInstance(event)
        }
    }
}