package com.jutt.moviesdetaildemo.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.jutt.moviesdetaildemo.MainCoroutineRule
import com.jutt.moviesdetaildemo.createMockDataSourceFactory
import com.jutt.moviesdetaildemo.data.data_sources.FlickrPhotoSearchFactory
import com.jutt.moviesdetaildemo.data.models.FlickrMappedPhoto
import com.jutt.moviesdetaildemo.data.models.Movie
import com.jutt.moviesdetaildemo.data.network.FakeNetworkManager
import com.jutt.moviesdetaildemo.data.network.NetworkManager
import com.jutt.moviesdetaildemo.data.repositories.FakeMovieRepository
import com.jutt.moviesdetaildemo.getOrAwaitValueTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class HomeViewModelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeViewModel
    private val fakeMovieRepository: FakeMovieRepository by lazy { FakeMovieRepository() }

    @Before
    fun setUpPre(){
        viewModel = HomeViewModel(moviesRepository = fakeMovieRepository)
    }

    @Test
    fun `Check if Searching Query Movies Query Returns Valid Output`(){
        viewModel.searchMovies("A")
        val returnedVal = viewModel.searchedMovies.getOrAwaitValueTest()
        Truth.assertThat(returnedVal).containsExactlyElementsIn(fakeMovieRepository.searchMoviesExpectedOutput())
    }
}