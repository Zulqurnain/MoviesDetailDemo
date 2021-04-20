package com.jutt.citiessearchmvvm.data.network

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.jutt.citiessearchmvvm.data.models.GeoCity
import com.jutt.citiessearchmvvm.extensions.string
import kotlinx.android.parcel.Parcelize
import retrofit2.Response

data class NetworkResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null,
    val responseCode: Int = 0,
    val requestStatus: RequestStatus = RequestStatus.COMPLETED
) { companion object }

enum class RequestStatus {
    ONGOING,
    COMPLETED
}

inline fun <reified T, reified E> Response<T>.toNetworkResponse(bodyPredicate: Response<T>.() -> E?) =
    NetworkResponse(
        success = isSuccessful,
        message = if(isSuccessful.not()) this.string else "",
        data = bodyPredicate(),
        responseCode = this.code()
    )

@kotlinx.parcelize.Parcelize
data class SearchCitiesResponse(
    @SerializedName("geonames")
    val geonames: List<GeoCity?>? = null,
    @SerializedName("totalResultsCount")
    val totalResultsCount: Int? = null // 12080935
) : Parcelable
