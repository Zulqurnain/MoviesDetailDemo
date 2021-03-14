package com.jutt.moviesdetaildemo.viewmodels

import androidx.lifecycle.*
import com.jutt.moviesdetaildemo.R
import com.jutt.moviesdetaildemo.architecture.Event
import com.jutt.moviesdetaildemo.data.models.Movie
import com.jutt.moviesdetaildemo.data.repositories.MoviesRepository
import com.jutt.moviesdetaildemo.data.repositories.DatabaseRepository
import com.jutt.moviesdetaildemo.data.repositories.ResourcesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val resourcesRepository: ResourcesRepository,
    private val moviesRepository: MoviesRepository,
    private val databaseRepository: DatabaseRepository
) : BaseViewModel(resourcesRepository) {

    object Events {
        const val NAVIGATE_TO_HOME: String = "NAVIGATE_TO_HOME"
        const val NAVIGATE_TO_HOME_DETAILS: String = "NAVIGATE_TO_HOME_DETAILS"
    }

    private val _navigate = MutableLiveData<Event<String>>()
    val navigate: LiveData<Event<String>> get() = _navigate

    private val _successMessage = MutableLiveData<Event<String>>()
    val successMessage: LiveData<Event<String>> get() = _successMessage

    val moviesList: LiveData<List<Movie>> get() = databaseRepository.getAllMovies()

    private val _selectedMovie = MutableLiveData<Event<Movie>>()
    val selectedMovie: LiveData<Event<Movie>> get() = _selectedMovie

    private val _toolbarVisible = MutableLiveData<Boolean>()
    val toolbarVisible: LiveData<Boolean> get() = _toolbarVisible

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

    fun navigateToHomeDetails(selectedMovie: Movie) {
        navigateToSection(Events.NAVIGATE_TO_HOME_DETAILS)
        _selectedMovie.postValue(Event.create(selectedMovie))
    }

    fun navigateToSection(eventContent: String) {
        _navigate.value = Event.create(content = eventContent)
    }

    fun syncMoviesFromLocal() {
        viewModelScope.launch {
            _showLoader.value = true
            val response = moviesRepository.fetchCatFactsFromAPI()
            val listReturned = response.data ?: arrayListOf()
            if (response.success && listReturned.isNotEmpty()) {
                databaseRepository.clearAllMovies()
                databaseRepository.upsertMovies(*listReturned.toTypedArray())
            } else {
                showErrorDialog(
                    messageToShow = response.message,
                    messageStrRes = R.string.error_message_server_error
                )
            }
            _showLoader.value = false
        }
    }

    fun setToolbarVisibility(visible: Boolean) {
        _toolbarVisible.postValue(visible)
    }
}