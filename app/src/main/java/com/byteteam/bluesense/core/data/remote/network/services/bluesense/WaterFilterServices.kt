package com.byteteam.bluesense.core.data.remote.network.services.bluesense

import com.byteteam.bluesense.core.data.remote.network.response.water_filters.GetWaterFilters
import com.byteteam.bluesense.core.data.remote.network.response.water_suppliers.GetWaterSuppliers
import retrofit2.http.GET
import retrofit2.http.Header


interface WaterFilterServices {
    @GET("api/store/water-filters")
    suspend fun getWaterFilters(
        @Header("Authorization") authToken: String
    ): GetWaterFilters

    @GET("api/store/water-filters/true")
    suspend fun getFeaturedWaterFilter(
        @Header("Authorization") authToken: String
    ): GetWaterFilters
}