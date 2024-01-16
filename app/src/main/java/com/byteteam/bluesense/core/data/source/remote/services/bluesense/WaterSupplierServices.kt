package com.byteteam.bluesense.core.data.source.remote.services.bluesense

import com.byteteam.bluesense.core.data.source.remote.response.water_suppliers.GetWaterSuppliers
import retrofit2.http.GET
import retrofit2.http.Header


interface WaterSupplierServices {
    @GET("api/store/water-suppliers")
    suspend fun getWaterSuppliers(
        @Header("Authorization") authToken: String
    ): GetWaterSuppliers
}