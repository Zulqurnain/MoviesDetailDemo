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

inline fun <reified T> NetworkResponse.Companion.createOnGoing(data: T? = null): NetworkResponse<T> {
    return NetworkResponse(success = false, requestStatus = RequestStatus.ONGOING, data = data)
}

inline fun <reified T> NetworkResponse.Companion.createSuccess(bodyPredicate: () -> T?): NetworkResponse<T> {
    return NetworkResponse(success = true, data = bodyPredicate())
}

inline fun <reified T> NetworkResponse.Companion.createFailure(
    code: Int,
    data: T? = null
): NetworkResponse<T> {
    return NetworkResponse(success = false, responseCode = code)
}

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
