package com.byteteam.bluesense.core.domain.repositories

import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.CityEntity
import com.byteteam.bluesense.core.domain.model.DistrictEntity
import com.byteteam.bluesense.core.domain.model.ProvinceEntity
import kotlinx.coroutines.flow.Flow

interface LocalAddressRepository {
    fun getProvince(): Flow<Resource<List<ProvinceEntity>>>
    fun getCities(provinceId: Int): Flow<Resource<List<CityEntity>>>
    fun getDistricts(cityId: Int): Flow<Resource<List<DistrictEntity>>>
}