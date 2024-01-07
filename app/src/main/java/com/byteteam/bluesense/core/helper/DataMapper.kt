package com.byteteam.bluesense.core.helper

import com.byteteam.bluesense.core.data.remote.network.response.local_address_api.GetCities
import com.byteteam.bluesense.core.data.remote.network.response.local_address_api.GetDistricts
import com.byteteam.bluesense.core.data.remote.network.response.local_address_api.GetProvinces
import com.byteteam.bluesense.core.domain.model.CityEntity
import com.byteteam.bluesense.core.domain.model.DistrictEntity
import com.byteteam.bluesense.core.domain.model.ProvinceEntity

fun GetProvinces.toProvinceEntities(): List<ProvinceEntity> = this.result?.mapNotNull {
    ProvinceEntity(id= it!!.id!!, text = it.text!!)
} ?: listOf<ProvinceEntity>()
fun GetCities.toCityEntities(): List<CityEntity> = this.result?.mapNotNull {
    CityEntity(id= it!!.id!!, text = it.text!!)
} ?: listOf<CityEntity>()
fun GetDistricts.toDistrictEntities(): List<DistrictEntity> = this.result?.mapNotNull {
    DistrictEntity(id= it!!.id!!, text = it.text!!)
} ?: listOf<DistrictEntity>()