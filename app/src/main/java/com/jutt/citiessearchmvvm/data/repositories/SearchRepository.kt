package com.jutt.citiessearchmvvm.data.repositories

import com.jutt.citiessearchmvvm.data.models.GeoCity
import com.jutt.citiessearchmvvm.data.network.NetworkManager
import com.jutt.citiessearchmvvm.data.network.NetworkResponse
import com.jutt.citiessearchmvvm.data.network.SearchCitiesResponse
import com.jutt.citiessearchmvvm.data.network.toNetworkResponse
import com.jutt.citiessearchmvvm.di.NamedHilts
import com.jutt.citiessearchmvvm.extensions.string
import com.jutt.citiessearchmvvm.extensions.swap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONArray
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val networkManager: NetworkManager,
    @Named(NamedHilts.REPOSITORY_DISPATCHER)
    private val dispatcher: CoroutineDispatcher
) : SearchLogicOperations() {

    override suspend fun searchCities(searchQuery: String, maximumRecordsPerYear: Int) : NetworkResponse<List<GeoCity>> =
        withContext(dispatcher) {
            val response = networkManager.execute(
                networkManager.searchCitiesAPI(
                    searchText = searchQuery,
                    noOfRecords = maximumRecordsPerYear
                )
            )
            return@withContext response.toNetworkResponse {
                body()?.geonames?.filterNotNull() ?: listOf()
            }
        }
}

abstract class SearchLogicOperations {

    abstract suspend fun searchCities(
        searchQuery: String,
        maximumRecords: Int
    ): NetworkResponse<List<GeoCity>>
}