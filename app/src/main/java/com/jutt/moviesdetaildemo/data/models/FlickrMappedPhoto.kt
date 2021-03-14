package com.jutt.moviesdetaildemo.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlickrMappedPhoto(
    val id: String? = "",
    val urlOfImage: String? = "",
    val title: String? = "",
): Parcelable