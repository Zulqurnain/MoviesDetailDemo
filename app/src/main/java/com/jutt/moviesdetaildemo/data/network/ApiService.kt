package com.jutt.moviesdetaildemo.data.network

import com.jutt.moviesdetaildemo.data.models.Movie
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("/facts")
    fun getCatTopFacts(): Call<List<Movie>>

//    @POST("/signup")
//    fun signUp(
//        @HeaderMap headers: Map<String, String>,
//        @Body body: Map<String, @JvmSuppressWildcards Any?>
//    ): Call<SignUpResponse>
}
