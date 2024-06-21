package com.byteteam.bluesense.core.data.source.remote.services.bluesense

import com.byteteam.bluesense.core.data.source.remote.response.water_supplier_detail.WaterSupplierDetailResponse
import com.byteteam.bluesense.core.data.source.remote.response.water_suppliers.GetWaterSuppliers
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface WaterSupplierServices {
    @GET("api/store/water-suppliers")
    suspend fun getWaterSuppliers(
        @Header("Authorization") authToken: String
    ): GetWaterSuppliers
    @GET("api/users/water-suppliers/logs/{id}")
    suspend fun getWaterSupplier(
        @Header("Authorization") authToken: String,
        @Path("id") id: String
    ): WaterSupplierDetailResponse
}