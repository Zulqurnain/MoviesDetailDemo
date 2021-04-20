package com.jutt.citiessearchmvvm.data.repositories

import com.jutt.citiessearchmvvm.data.models.GeoCity
import com.jutt.citiessearchmvvm.data.network.FakeNetworkManager
import com.jutt.citiessearchmvvm.data.network.FakeNetworkManagerAndroidTest
import com.jutt.citiessearchmvvm.data.network.NetworkResponse
import org.mockito.Mockito

class FakeSearchRepositoryAndroidTest : SearchLogicOperations() {

    private val networkManager by lazy { FakeNetworkManagerAndroidTest() }

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