package com.byteteam.bluesense.core.data.remote.network.services.bluesense

import com.byteteam.bluesense.core.data.remote.network.response.MessageResponse
import com.byteteam.bluesense.core.data.remote.network.response.devices.GetDeviceLatestInfoResponse
import com.byteteam.bluesense.core.data.remote.network.response.devices.GetDevicesResponse
import com.byteteam.bluesense.core.data.remote.network.response.devices.GetDevicesResponseOld
import com.byteteam.bluesense.core.data.remote.network.response.water_suppliers.GetWaterSuppliers
import com.byteteam.bluesense.core.domain.model.DevicePost
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


interface WaterSupplierServices {
    @GET("api/store/water-suppliers")
    suspend fun getWaterSuppliers(
        @Header("Authorization") authToken: String
    ): GetWaterSuppliers
}