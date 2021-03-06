package com.jutt.moviesdetaildemo.data.network

import com.jutt.moviesdetaildemo.BuildConfig
import com.jutt.moviesdetaildemo.data.models.flickr.FlickrPhoto
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkManager @Inject constructor(private val apiService: ApiService) {

    fun <T> execute(call: Call<T>): Response<T?> {
        return try {
            val response = call.execute()
            if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
//                TODO if user is Logged in Revoke Authentication and LOGOUT
            }
            response
        } catch (ex: Exception) {
            Timber.e(ex)
            when (ex) {
                is ConnectException, is UnknownHostException, is SocketException -> {
                    Response.error(
                        HttpURLConnection.HTTP_UNAVAILABLE,
                        textToResponseBody(text = "{'message': 'Internet not available. Please check your Internet settings and try again.'}")
                    )
                }
                else ->
                    Response.error(
                        HttpURLConnection.HTTP_UNAVAILABLE,
                        textToResponseBody(text = "Server not available")
                    )
            }
        }
    }

    private fun textToRequestBody(text: String): RequestBody {
        return text.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    private fun textToResponseBody(text: String): ResponseBody {
        return text.toResponseBody("text/plain".toMediaTypeOrNull())
    }

    /////////////////////////////////////////////////////////////////

    fun getImagesFromFlickr(
        flickrAPIKey: String = BuildConfig.FLICKR_API_KEY,
        searchText: String,
        page: Int,
        pageSize: Int
    ): Call<PaginationPhotosResponse<List<FlickrPhoto>>> {
        val body = mutableMapOf<String,Any?>(
            "method" to "flickr.photos.search",
            "api_key" to flickrAPIKey,
            "format" to "json",
            "nojsoncallback" to 1,
            "text" to searchText,
            "page" to page,
            "per_page" to pageSize
        )
        return apiService.imagesSearchFlickr(options = body)
    }

}
