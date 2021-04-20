package com.jutt.citiessearchmvvm.data.models


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class GeoCity(
    @SerializedName("geonameId")
    val id: Long = 0, // auto increment
    @SerializedName("adminCode1")
    val adminCode1: String? = "", // G1
    @SerializedName("adminCodes1")
    val adminCodes1: AdminCodes1? = null,
    @SerializedName("adminName1")
    val adminName1: String? = "", // Waikato
    @SerializedName("countryCode")
    val countryCode: String? = "", // NZ
    @SerializedName("countryId")
    val countryId: String? = "", // 2186224
    @SerializedName("countryName")
    val countryName: String? = "", // New Zealand
    @SerializedName("fcl")
    val fcl: String? = "", // H
    @SerializedName("fclName")
    val fclName: String? = "", // stream, lake, ...
    @SerializedName("fcode")
    val fcode: String? = "", // BAY
    @SerializedName("fcodeName")
    val fcodeName: String? = "", // bay
    @SerializedName("lat")
    val lat: String? = "", // -36.74848
    @SerializedName("lng")
    val lng: String? = "", // 175.47277
    @SerializedName("name")
    val name: String? = "", // Long
    @SerializedName("population")
    val population: Int? = 0, // 0
    @SerializedName("toponymName")
    val toponymName: String? = "" // Long
) : Parcelable {
    @Parcelize
    @Serializable
    data class AdminCodes1(
        @SerializedName("ISO3166_2")
        val iSO31662: String? = "" // WKO
    ) : Parcelable
}