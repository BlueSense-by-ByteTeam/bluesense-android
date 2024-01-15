package com.byteteam.bluesense.core.helper

import com.byteteam.bluesense.core.data.remote.network.response.devices.GetDeviceLatestInfoResponse
import com.byteteam.bluesense.core.data.remote.network.response.devices.GetDevicesResponse
import com.byteteam.bluesense.core.data.remote.network.response.devices.GetDevicesResponseOld
import com.byteteam.bluesense.core.data.remote.network.response.indonesian_location.GetCities
import com.byteteam.bluesense.core.data.remote.network.response.indonesian_location.GetCitiesItem
import com.byteteam.bluesense.core.data.remote.network.response.indonesian_location.GetDistricts
import com.byteteam.bluesense.core.data.remote.network.response.indonesian_location.GetDistrictsItem
import com.byteteam.bluesense.core.data.remote.network.response.indonesian_location.GetProvince
import com.byteteam.bluesense.core.data.remote.network.response.indonesian_location.GetProvinceItem
import com.byteteam.bluesense.core.data.remote.network.response.water_filters.GetWaterFilters
import com.byteteam.bluesense.core.data.remote.network.response.water_suppliers.GetWaterSuppliers
import com.byteteam.bluesense.core.domain.model.CityEntity
import com.byteteam.bluesense.core.domain.model.DeviceEntity
import com.byteteam.bluesense.core.domain.model.DeviceLatestInfoEntity
import com.byteteam.bluesense.core.domain.model.DistrictEntity
import com.byteteam.bluesense.core.domain.model.ProvinceEntity
import com.byteteam.bluesense.core.domain.model.WaterFilterEntity
import com.byteteam.bluesense.core.domain.model.WaterSupplierEntity

fun GetProvince.toProvinceEntity(): List<ProvinceEntity> = this.getProvince?.mapNotNull {
    ProvinceEntity(id = it!!.code!!, text = it.name!!)
} ?: listOf<ProvinceEntity>()

fun GetCities.toCityEntities(): List<CityEntity> = this.getCities?.mapNotNull {
    CityEntity(id = it!!.code!!, text = it.name!!)
} ?: listOf<CityEntity>()

fun GetDistricts.toDistrictEntities(): List<DistrictEntity> = this.getDistricts?.mapNotNull {
    DistrictEntity(id = it!!.code!!, text = it.name!!)
} ?: listOf<DistrictEntity>()

fun GetProvinceItem.toProvinceEntity(): ProvinceEntity =
    ProvinceEntity(id = this!!.code!!, text = this.name!!)

fun GetCitiesItem.toCitiesEntity(): CityEntity = CityEntity(id = this!!.code!!, text = this.name!!)
fun GetDistrictsItem.toDistrictEntity(): DistrictEntity =
    DistrictEntity(id = this!!.code!!, text = this.name!!)

fun GetDevicesResponseOld.toDeviceEntities(): List<DeviceEntity> = this.data?.map {
    DeviceEntity(
        id = it?.id!!,
        userDeviceId = it?.id!!,
        name = it?.deviceDetail?.name!!,
        waterSource = it?.deviceDetail?.waterSource!!,
        address = it?.deviceDetail?.address!!,
        district = it?.deviceDetail?.address!!, // TODO: replace with district data
        city = it?.deviceDetail?.city!!,
        province = it?.deviceDetail?.province!!,
        mqttBaseUrl = "",
        macId = "",
        mqttTopic = ""
    )
} ?: listOf()

fun GetDevicesResponse.toDeviceEntities(): List<DeviceEntity> = this.data?.map {
    DeviceEntity(
        id = it?.deviceId!!,
        userDeviceId = it?.id!!,
        macId = it?.device?.deviceId!!,
        name = it?.deviceDetail?.name!!,
        waterSource = it?.deviceDetail?.waterSource!!,
        address = it?.deviceDetail?.address!!,
        district = it?.deviceDetail?.district!!,
        city = it?.deviceDetail?.city!!,
        province = it?.deviceDetail?.province!!,
        mqttBaseUrl = it?.device?.mqttBaseUrl!!,
        mqttTopic = it?.device.mqttTopic!!,
    )
} ?: listOf()

fun GetDeviceLatestInfoResponse.toDeviceLatestInfoEntity(id: String): DeviceLatestInfoEntity? = if(this.data?.log == null) null else DeviceLatestInfoEntity(
    id = id,
    status = this.data?.status!!,
    quality = this.data?.quality!!,
    ph = this.data?.log?.ph?.toDouble() ?: 0.0,
    tds = this.data?.log?.tds?.toDouble() ?: 0.0,
    timestamp=this.data?.log?.createdAt.toString()
)

fun GetWaterFilters.toWaterFilterEntities(): List<WaterFilterEntity> = this.data?.map {
    WaterFilterEntity(
        id= it?.id!!,
        name= it?.name!!,
        price= it?.price?.toLongOrNull() ?: 0,
        rating= it?.rating?.toDouble() ?: 0.0,
        description = it?.description ?: "",
        tokopediaUrl =  it?.tokopediaUrl ?: "",
        shoppeUrl =  it?.shoppeUrl ?: "",
        imageUrl =  it?.imageUrl ?: "",
        )
} ?: listOf()

fun GetWaterSuppliers.toWaterSupplierEntities(): List<WaterSupplierEntity> = this.data?.map {
    WaterSupplierEntity(
        id= it?.id!!,
        name= it?.name!!,
        phone = it?.phone ?: "-",
        instagramUrl =  it?.instagramUrl ?: "-",
        category =  it?.category ?: "",
        imageUrl =  it?.imageUrl ?: "",
    )
} ?: listOf()