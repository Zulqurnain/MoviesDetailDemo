package com.jutt.moviesdetaildemo.data.models


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Entity
@Parcelize
@Serializable
data class Movie(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Long = 0, // auto increment
    @SerializedName("cast")
    val cast: List<String> = listOf(),
    @SerializedName("genres")
    val genres: List<String> = listOf(),
    @SerializedName("rating")
    val rating: Int = 0, // 1
    @SerializedName("title")
    val title: String = "", // (500) Days of Summer
    @SerializedName("year")
    val year: Int = -1 // 2009
) : Parcelable