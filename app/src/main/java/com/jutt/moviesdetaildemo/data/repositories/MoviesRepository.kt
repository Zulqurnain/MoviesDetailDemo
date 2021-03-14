package com.jutt.moviesdetaildemo.data.repositories

import com.jutt.moviesdetaildemo.data.models.Movie
import com.jutt.moviesdetaildemo.data.network.NetworkManager
import com.jutt.moviesdetaildemo.data.network.NetworkResponse
import com.jutt.moviesdetaildemo.data.network.toNetworkResponse
import com.jutt.moviesdetaildemo.di.NamedHilts
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val networkManager: NetworkManager,
    @Named(NamedHilts.REPOSITORY_DISPATCHER)
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun fetchCatFactsFromAPI(): NetworkResponse<List<Movie>> =
        withContext(dispatcher) {
            val response = networkManager.execute(
                networkManager.getCatTopFacts()
            )
            val body = response.body()

            return@withContext response.toNetworkResponse {
                var returnList = emptyList<Movie>()
                if (isSuccessful){
                    returnList = body?.toList() ?: emptyList()
                }
                returnList
            }
        }
}