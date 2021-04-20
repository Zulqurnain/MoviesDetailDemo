package com.jutt.citiessearchmvvm.data.network

import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("https://secure.geonames.org/searchJSON")
    fun searchCities(
        @QueryMap options:Map<String, @JvmSuppressWildcards Any?>,
    ): Call<SearchCitiesResponse>

}
