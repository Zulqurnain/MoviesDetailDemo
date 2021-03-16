package com.jutt.moviesdetaildemo.data.repositories

import com.jutt.moviesdetaildemo.createMockDataSourceFactory
import com.jutt.moviesdetaildemo.data.data_sources.FlickrPhotoSearchFactory
import com.jutt.moviesdetaildemo.data.models.FlickrMappedPhoto
import com.jutt.moviesdetaildemo.data.models.Movie
import com.jutt.moviesdetaildemo.data.network.FakeNetworkManager
import com.jutt.moviesdetaildemo.data.network.NetworkResponse
import kotlinx.coroutines.withContext
import org.mockito.Mockito

class FakeMovieRepository : MoviesLogicOperations() {

    private val networkManager by lazy { FakeNetworkManager() }
    private val databaseRepository by lazy { FakeDatabaseRepository() }

    override suspend fun syncFetchMoviesList(): List<Movie> = databaseRepository.getAllMovies()

    fun searchMoviesExpectedOutput() = databaseRepository.searchMoviesExpectedOutput()

    /**
     * Always Return Single Scenerio
     * searchQuery = "A"
     * maximumRecordsPerYear = 5
     *
     * @param searchQuery
     * @param maximumRecordsPerYear
     * @return
     */
    override suspend fun searchMovies(
        searchQuery: String,
        maximumRecordsPerYear: Int
    ): List<Any> {
            return getListOfMoviesSortedByYearSortedRating(
                listOfFetchedMovies = databaseRepository.searchMoviesInput(),
                searchQuery = searchQuery,
                maxRecordsPerYear = maximumRecordsPerYear
            )
    }

    override suspend fun getSingleImageFromFlickr(movieName: String): NetworkResponse<FlickrMappedPhoto> {
        return NetworkResponse(
            success = true,
            message = "",
            data = Mockito.any(FlickrMappedPhoto::class.java),
            responseCode = 200,
        )
    }

    override suspend fun getMoviesFromAssets(): ArrayList<Movie> = arrayListOf()

    override fun getFlickrPagingFactory(): FlickrPhotoSearchFactory? = Mockito.any()
}