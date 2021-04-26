package com.jutt.moviesdetaildemo.helper

data class AlertDialogParams(
    val title: String? = null,
    val message: String?,
    val positiveButtonText: String? = null,
    val showPositiveButton: Boolean = true,
    val negativeButtonText: String? = null,
    val showNegativeButton: Boolean = !negativeButtonText.isNullOrBlank(),
    val isCancelable: Boolean = true
)
