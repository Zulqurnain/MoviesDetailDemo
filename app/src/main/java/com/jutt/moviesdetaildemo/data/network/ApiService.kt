package com.jutt.moviesdetaildemo.data.network

import com.jutt.moviesdetaildemo.data.models.Movie
import com.jutt.moviesdetaildemo.data.models.flickr.FlickrPhoto
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key={API_KEY}&format=json&nojsoncallback=1&text={QUERY}&page={PAGE_NO}&per_page={PER_PAGE}")
    fun imagesSearchFlickr(
        @Path("API_KEY") apiKey: String,
        @Path("QUERY") query: String,
        @Path("PAGE_NO") pageNo: Int,
        @Path("PER_PAGE") perPage: Int
    ): Call<PaginationPhotosResponse<List<FlickrPhoto>>>

//    @POST("/signup")
//    fun signUp(
//        @HeaderMap headers: Map<String, String>,
//        @Body body: Map<String, @JvmSuppressWildcards Any?>
//    ): Call<SignUpResponse>
}
