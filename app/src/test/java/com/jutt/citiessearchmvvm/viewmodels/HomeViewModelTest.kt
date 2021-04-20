package com.jutt.citiessearchmvvm.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.jutt.citiessearchmvvm.MainCoroutineRule
import com.jutt.citiessearchmvvm.data.models.GeoCity
import com.jutt.citiessearchmvvm.data.repositories.FakeSearchRepository
import com.jutt.citiessearchmvvm.getOrAwaitValueTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeViewModel
    private val fakeSearchRepository: FakeSearchRepository by lazy { FakeSearchRepository() }

    @Before
    fun setUpPre(){
        viewModel = HomeViewModel(searchRepository = fakeSearchRepository)
    }

    @Test
    fun `Should Get Only 1 result for san francis `(){
        viewModel.searchMovies("san francis")
        val returnedVal = viewModel.searchedCities.getOrAwaitValueTest()
        Truth.assertThat(returnedVal).contains(
            GeoCity(
                adminCode1 = "CA",
                lng = "-122.41942",
                id = 5391959,
                toponymName = "San Francisco",
                countryId = "6252001",
                fcl = "P",
                population = 864816,
                countryCode = "US",
                name = "San Francisco",
                fclName = "city, village,...",
                adminCodes1 = GeoCity.AdminCodes1(
                    iSO31662 = "CA"
                ),
                countryName = "United States",
                fcodeName = "seat of a second-order administrative division",
                adminName1 = "California",
                lat = "37.77493",
                fcode = "PPLA2",
            )
        )
    }
}