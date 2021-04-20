package com.jutt.citiessearchmvvm.viewmodels

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import com.jutt.citiessearchmvvm.R
import com.jutt.citiessearchmvvm.application.Contants.DEFAULT_CITIES_MAX_SEARCH_DISTANCE
import com.jutt.citiessearchmvvm.architecture.Event
import com.jutt.citiessearchmvvm.data.models.GeoCity
import com.jutt.citiessearchmvvm.data.repositories.SearchLogicOperations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchRepository: SearchLogicOperations
) : BaseViewModel() {

    object Events {
        const val NAVIGATE_TO_SEARCH: String = "NAVIGATE_TO_HOME"
    }

    private val _navigate = MutableLiveData<Event<String>>()
    val navigate: LiveData<Event<String>> get() = _navigate

    private val _successMessage = MutableLiveData<Event<String>>()
    val successMessage: LiveData<Event<String>> get() = _successMessage

    private val _searchedCities = MutableLiveData<List<GeoCity>>()
    val searchedCities: LiveData<List<GeoCity>> get() = _searchedCities

    private val _selectedGeoCity = MutableLiveData<GeoCity>()
    val selectedGeoCity: LiveData<GeoCity> get() = _selectedGeoCity

    private val _toolbarVisible = MutableLiveData<Boolean>()
    val toolbarVisible: LiveData<Boolean> get() = _toolbarVisible

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun start() {
        navigateToSearch()
    }

    fun navigateToSearch() {
        navigateToSection(Events.NAVIGATE_TO_SEARCH)
    }

    fun navigateToSection(eventContent: String) {
        _navigate.value = Event.create(content = eventContent)
    }

    fun selectMovie(selectedGeoCity: GeoCity) {
        _selectedGeoCity.postValue(selectedGeoCity)
    }

    fun searchMovies(searchQuery: String, isErrorSilent: Boolean = true) {
        viewModelScope.launch {
            _showLoader.value = true

            val response = searchRepository.searchCities(
                searchQuery = searchQuery,
                maximumRecords = DEFAULT_CITIES_MAX_SEARCH_DISTANCE
            )

            if (response.success) {
                _searchedCities.postValue(response.data ?: listOf())
            } else {
                if(isErrorSilent.not()) {
                    showErrorDialog(
                        messageToShow = response.message,
                        messageStrRes = R.string.error_message_push_id_not_available
                    )
                }
            }

            _showLoader.value = false
        }
    }

    fun setToolbarVisibility(visible: Boolean) {
        _toolbarVisible.postValue(visible)
    }

}