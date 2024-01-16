package com.byteteam.bluesense.core.data.source.remote.services.bluesense

import com.byteteam.bluesense.core.data.source.remote.response.water_filters.GetWaterFilters
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