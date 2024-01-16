package com.byteteam.bluesense.core.data.source.remote.services.location

import com.byteteam.bluesense.core.data.source.remote.response.indonesian_location.GetCitiesItem
import com.byteteam.bluesense.core.data.source.remote.response.indonesian_location.GetDistrictsItem
import com.byteteam.bluesense.core.data.source.remote.response.indonesian_location.GetProvinceItem
import retrofit2.http.GET
import retrofit2.http.Path


interface IndonesianLocationAddressService{
    @GET("semantic/prov")
    suspend fun getProvinces(): List<GetProvinceItem>

    @GET("semantic/prov/{id}/kab")
    suspend fun getCities(@Path("id") id: Int): List<GetCitiesItem>

    @GET("semantic/prov/{province_id}/kab/{city_id}/kec")
    suspend fun getDistricts(@Path("province_id") provinceId: Int, @Path("city_id") cityId: Int): List<GetDistrictsItem>
}