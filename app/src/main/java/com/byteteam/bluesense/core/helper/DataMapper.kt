package com.byteteam.bluesense.core.helper

import com.byteteam.bluesense.core.data.remote.network.response.indonesian_location.GetCities
import com.byteteam.bluesense.core.data.remote.network.response.indonesian_location.GetCitiesItem
import com.byteteam.bluesense.core.data.remote.network.response.indonesian_location.GetDistricts
import com.byteteam.bluesense.core.data.remote.network.response.indonesian_location.GetDistrictsItem
import com.byteteam.bluesense.core.data.remote.network.response.indonesian_location.GetProvince
import com.byteteam.bluesense.core.data.remote.network.response.indonesian_location.GetProvinceItem
import com.byteteam.bluesense.core.domain.model.CityEntity
import com.byteteam.bluesense.core.domain.model.DistrictEntity
import com.byteteam.bluesense.core.domain.model.ProvinceEntity

fun GetProvince.toProvinceEntity(): List<ProvinceEntity> = this.getProvince?.mapNotNull {
    ProvinceEntity(id= it!!.code!!, text = it.name!!)
} ?: listOf<ProvinceEntity>()
fun GetCities.toCityEntities(): List<CityEntity> = this.getCities?.mapNotNull {
    CityEntity(id= it!!.code!!, text = it.name!!)
} ?: listOf<CityEntity>()
fun GetDistricts.toDistrictEntities(): List<DistrictEntity> = this.getDistricts?.mapNotNull {
    DistrictEntity(id= it!!.code!!, text = it.name!!)
} ?: listOf<DistrictEntity>()
fun GetProvinceItem.toProvinceEntity(): ProvinceEntity =  ProvinceEntity(id= this!!.code!!, text = this.name!!)
fun GetCitiesItem.toCitiesEntity(): CityEntity =  CityEntity(id= this!!.code!!, text = this.name!!)
fun GetDistrictsItem.toDistrictEntity(): DistrictEntity =  DistrictEntity(id= this!!.code!!, text = this.name!!)
