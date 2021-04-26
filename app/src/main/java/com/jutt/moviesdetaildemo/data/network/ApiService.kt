package com.jutt.moviesdetaildemo.data.network

import com.jutt.moviesdetaildemo.data.models.Movie
import com.jutt.moviesdetaildemo.data.models.flickr.FlickrPhoto
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("https://api.flickr.com/services/rest/")
    fun imagesSearchFlickr(
        @QueryMap options:Map<String, @JvmSuppressWildcards Any?>,
    ): Call<PaginationPhotosResponse<List<FlickrPhoto>>>

}
