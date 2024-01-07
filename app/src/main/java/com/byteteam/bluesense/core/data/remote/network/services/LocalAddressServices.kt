package com.byteteam.bluesense.core.data.remote.network.services

import com.byteteam.bluesense.core.data.remote.network.response.local_address_api.GetCities
import com.byteteam.bluesense.core.data.remote.network.response.local_address_api.GetDistricts
import com.byteteam.bluesense.core.data.remote.network.response.local_address_api.GetProvinces
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface LocalAddressServices{
    @GET("/provinsi/get")
    suspend fun getProvinces(): GetProvinces

    @GET("/kabkota/get/")
    fun getCities(@Query("d_provinsi_id") id: Int): GetCities

    @GET("/kecamatan/get/")
    fun getDistricts(@Query("d_kabkota_id") id: Int): GetDistricts
}