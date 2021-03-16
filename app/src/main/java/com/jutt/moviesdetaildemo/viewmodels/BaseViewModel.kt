package com.jutt.moviesdetaildemo.viewmodels

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.StringUtils
import com.jutt.moviesdetaildemo.architecture.Event
import com.jutt.moviesdetaildemo.data.repositories.ResourcesRepository
import com.jutt.moviesdetaildemo.helper.AlertDialogParams
import kotlinx.coroutines.cancel
abstract class BaseViewModel : ViewModel() {

    protected val _showLoader = MutableLiveData<Boolean>()
    private val _popupMessage = MutableLiveData<Event<AlertDialogParams>>()

    val showLoader: LiveData<Boolean>
        get() = _showLoader
    val popupMessage: LiveData<Event<AlertDialogParams>>
        get() = _popupMessage

    fun showErrorDialog(
        @StringRes messageStrRes: Int,
        messageToShow: String? = null,
        @StringRes titleRes: Int? = null,
        title: String? = null,
    ) {
        _popupMessage.value =
            Event.create(
                content = AlertDialogParams(
                    title = title ?: titleRes?.let { StringUtils.getString(it)} ?: "",
                    message = messageToShow ?: StringUtils.getString(messageStrRes)
                )
            )
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}