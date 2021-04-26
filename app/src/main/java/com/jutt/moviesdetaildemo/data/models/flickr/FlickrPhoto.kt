package com.jutt.moviesdetaildemo.data.models.flickr


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class FlickrPhoto(
    @SerializedName("farm")
    val farm: Int? = null, // 66
    @SerializedName("id")
    val id: String? = null, // 51034361931
    @SerializedName("isfamily")
    val isfamily: Int? = null, // 0
    @SerializedName("isfriend")
    val isfriend: Int? = null, // 0
    @SerializedName("ispublic")
    val ispublic: Int? = null, // 1
    @SerializedName("owner")
    val owner: String? = null, // 67627528@N04
    @SerializedName("secret")
    val secret: String? = null, // 8dfbdc200d
    @SerializedName("server")
    val server: String? = null, // 65535
    @SerializedName("title")
    val title: String? = null // "ugly ducklings" - young australasian darters
) : Parcelable