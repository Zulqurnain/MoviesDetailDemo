package com.jutt.moviesdetaildemo.data.network

import com.jutt.moviesdetaildemo.BuildConfig
import com.jutt.moviesdetaildemo.data.models.flickr.FlickrPhoto
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.mockito.Mockito
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

class FakeNetworkManagerAndroidTest{

    fun getImagesFromFlickr(
        flickrAPIKey: String = BuildConfig.FLICKR_API_KEY,
        searchText: String,
        page: Int,
        pageSize: Int
    ): PaginationPhotosResponse<List<FlickrPhoto>>? {
        if(
            flickrAPIKey.isEmpty() ||
                    searchText.isEmpty() ||
                    page < 0 ||
                    pageSize < 0
        ){ return null}

        return Mockito.any()
    }

}