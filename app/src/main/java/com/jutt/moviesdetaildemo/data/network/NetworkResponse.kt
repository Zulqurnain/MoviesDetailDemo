package com.jutt.moviesdetaildemo.data.network

import com.google.gson.annotations.SerializedName
import com.jutt.moviesdetaildemo.extensions.string
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

class PaginationPhotosResponse<T>(
    @SerializedName("stat") val status: String,
    @SerializedName("code") val statusCode: Int,
    val message: String?,
    val photos: PaginationPhotosData<T>?,
)

class PaginationPhotosData<T>(
    val page : Int,
    val pages : Int,
    val perpage : Int,
    val total : Int,
    val photo : T,
)
