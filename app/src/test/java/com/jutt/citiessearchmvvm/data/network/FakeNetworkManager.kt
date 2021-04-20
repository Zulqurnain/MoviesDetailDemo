package com.jutt.citiessearchmvvm.data.network

import com.jutt.citiessearchmvvm.BuildConfig
import com.jutt.citiessearchmvvm.data.models.GeoCity

import org.mockito.Mockito
import retrofit2.Call

class FakeNetworkManager{

    fun searchCitiesAPIFor_san_francis(
    ): List<GeoCity> {
        return listOfNotNull(
            GeoCity(
                adminCode1 = "CA",
                lng = "-122.41942",
                id = 5391959,
                toponymName = "San Francisco",
                countryId = "6252001",
                fcl = "P",
                population = 864816,
                countryCode = "US",
                name = "San Francisco",
                fclName = "city, village,...",
                adminCodes1 = GeoCity.AdminCodes1(
                    iSO31662 = "CA"
                ),
                countryName = "United States",
                fcodeName = "seat of a second-order administrative division",
                adminName1 = "California",
                lat = "37.77493",
                fcode = "PPLA2",
            )
        )
    }

}