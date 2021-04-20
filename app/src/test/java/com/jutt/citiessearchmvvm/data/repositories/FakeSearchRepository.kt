package com.jutt.citiessearchmvvm.data.repositories

import com.jutt.citiessearchmvvm.data.models.GeoCity
import com.jutt.citiessearchmvvm.data.network.FakeNetworkManager
import com.jutt.citiessearchmvvm.data.network.NetworkResponse
import com.jutt.citiessearchmvvm.data.network.toNetworkResponse
import org.mockito.Mockito

class FakeSearchRepository : SearchLogicOperations() {

    private val networkManager by lazy { FakeNetworkManager() }

    override suspend fun searchCities(
        searchQuery: String,
        maximumRecords: Int
    ): NetworkResponse<List<GeoCity>> {
        val list = networkManager.searchCitiesAPIFor_san_francis()
        return NetworkResponse(
            success = true,
            data = list
        )
    }

}