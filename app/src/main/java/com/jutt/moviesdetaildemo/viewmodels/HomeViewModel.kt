package com.jutt.moviesdetaildemo.viewmodels

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jutt.moviesdetaildemo.R
import com.jutt.moviesdetaildemo.application.Contants.paginationConfig
import com.jutt.moviesdetaildemo.architecture.Event
import com.jutt.moviesdetaildemo.data.data_sources.FlickrPhotoSearchFactory
import com.jutt.moviesdetaildemo.data.models.FlickrMappedPhoto
import com.jutt.moviesdetaildemo.data.models.Movie
import com.jutt.moviesdetaildemo.data.repositories.MoviesRepository
import com.jutt.moviesdetaildemo.data.repositories.ResourcesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    resourcesRepository: ResourcesRepository,
    private val moviesRepository: MoviesRepository
) : BaseViewModel(resourcesRepository) {

    object Events {
        const val NAVIGATE_TO_HOME: String = "NAVIGATE_TO_HOME"
        const val NAVIGATE_TO_HOME_DETAILS: String = "NAVIGATE_TO_HOME_DETAILS"
    }

    private val _navigate = MutableLiveData<Event<String>>()
    val navigate: LiveData<Event<String>> get() = _navigate

    private val _successMessage = MutableLiveData<Event<String>>()
    val successMessage: LiveData<Event<String>> get() = _successMessage

    private val _searchedMovies = MutableLiveData<List<Any>>()
    val searchedMovies: LiveData<List<Any>> get() = _searchedMovies

    private val _moviesList = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>> get() = _moviesList

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie> get() = _selectedMovie

    private val _movieCoverPhotoGot = MutableLiveData<FlickrMappedPhoto>()
    val movieCoverPhotoGot: LiveData<FlickrMappedPhoto> get() = _movieCoverPhotoGot

    private val _toolbarVisible = MutableLiveData<Boolean>()
    val toolbarVisible: LiveData<Boolean> get() = _toolbarVisible

    //////////////////////////////////////////////////////////////

    private lateinit var _flickrPhotosPaginatedData: LiveData<PagedList<FlickrMappedPhoto>>
    private val flickrPhotosDataFactory = MutableLiveData<FlickrPhotoSearchFactory>()
    val startPagingFickrPhotos = MutableLiveData<Event<Boolean>>()
    val paginatedFickrPhotos: LiveData<PagedList<FlickrMappedPhoto>>
        get() = _flickrPhotosPaginatedData

    //////////////////////////////////////////////////////////////


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun start() {
        navigateToHome()
    }

    fun navigateToHome() {
        navigateToSection(Events.NAVIGATE_TO_HOME)
    }

    fun navigateToHomeDetails() {
        navigateToSection(Events.NAVIGATE_TO_HOME_DETAILS)
    }

    fun navigateToSection(eventContent: String) {
        _navigate.value = Event.create(content = eventContent)
    }

    fun selectMovie(selectedMovie: Movie) {
        _selectedMovie.postValue(selectedMovie)
    }

    fun fetchMoviesData() {
        viewModelScope.launch {
            _showLoader.value = true
            val movies = moviesRepository.syncFetchMoviesList()
            _moviesList.value = movies
            _showLoader.value = false
        }
    }

    fun searchMovies(searchQuery: String) {
        viewModelScope.launch {
            if (searchQuery.isEmpty()) {
                _showLoader.postValue(true)
                _searchedMovies.postValue(listOf())
                _moviesList.postValue(moviesList.value)
                _showLoader.postValue(false)
            } else {
                _searchedMovies.postValue(
                    moviesRepository.searchMovies(searchQuery)
                )
            }
        }
    }


    fun getMovieSingleImageFromFlickr(movieName: String) {
        viewModelScope.launch {
            _showLoader.value = true
            val response =
                moviesRepository.getSingleImageFromFlickr(movieName)
            _showLoader.value = false
            if (response.success && response.data != null) {
                response.data.let { _movieCoverPhotoGot.postValue(it) }
            } else {
                showErrorDialog(
                    messageToShow = response.message,
                    messageStrRes = R.string.error_message_push_id_not_available
                )
            }
            _showLoader.value = false
        }
    }

    fun setToolbarVisibility(visible: Boolean) {
        _toolbarVisible.postValue(visible)
    }

    fun fetchImagesForString(
        searchQuery: String,
        forcedReload: Boolean = true
    ) {
        initFlickrImagesPagination(
            searchQuery = searchQuery,
            forcedReload
        )
        if (startPagingFickrPhotos.value == null) {
            startPagingFickrPhotos.value = Event.create(content = true)
        }
        flickrPhotosDataFactory.value?.flickrImagesDataSourceLiveData?.value?.invalidate()
    }

    /**
     * [IMPORTANT] Only call this method first time per screen
     * because it resets attributes of pagination and your items will reload from scratch
     *
     * @param searchQuery
     * @param reloadFromFirstItem true to reload from scratch , false otherwise
     */
    private fun initFlickrImagesPagination(
        searchQuery: String,
        reloadFromFirstItem: Boolean = true
    ) {
        /**
         * making sure items are reloaded from scratch or not
         */
        if (reloadFromFirstItem.not()
            && flickrPhotosDataFactory.value != null
            && flickrPhotosDataFactory.value?.searchText == searchQuery
        ) return

        /**
         * Configuring Pagination
         */
        val config = paginationConfig.build()
        val dataFactory = FlickrPhotoSearchFactory(
            searchText = searchQuery,
            networkManager = moviesRepository.networkManager,
            showLoader = _showLoader
        )

        flickrPhotosDataFactory.value = dataFactory
        _flickrPhotosPaginatedData = LivePagedListBuilder(dataFactory, config).build()

        /**
         * refreshing list to update LIVEDATA so UI will update
         */
        startPagingFickrPhotos.value = Event.create(true)

        /**
         * Invalidate the data
         */
//        flickrPhotosDataFactory.value?.refresh()
    }
}