package com.jutt.moviesdetaildemo.viewmodels

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jutt.moviesdetaildemo.architecture.Event
import com.jutt.moviesdetaildemo.data.repositories.ResourcesRepository
import com.jutt.moviesdetaildemo.helper.AlertDialogParams
import kotlinx.coroutines.cancel
abstract class BaseViewModel constructor(
    private val resourcesRepository: ResourcesRepository
) : ViewModel() {

    protected val _showLoader = MutableLiveData<Boolean>()
    protected val _popupMessage = MutableLiveData<Event<AlertDialogParams>>()
    protected val _showOTPDialog = MutableLiveData<Event<Boolean>>()

    val showLoader: LiveData<Boolean>
        get() = _showLoader
    val popupMessage: LiveData<Event<AlertDialogParams>>
        get() = _popupMessage
    val showOTPDialog: LiveData<Event<Boolean>>
        get() = _showOTPDialog

    fun showErrorDialog(
        @StringRes messageStrRes: Int,
        messageToShow: String? = null,
        @StringRes titleRes: Int? = null,
        title: String? = null,
    ) {
        _popupMessage.value =
            Event.create(
                content = AlertDialogParams(
                    title = title ?: titleRes?.let { resourcesRepository.getString(it)} ?: "",
                    message = messageToShow ?: resourcesRepository.getString(messageStrRes)
                )
            )
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}