package com.byteteam.bluesense.core.domain.repositories

import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.CityEntity
import com.byteteam.bluesense.core.domain.model.DistrictEntity
import com.byteteam.bluesense.core.domain.model.ProvinceEntity
import kotlinx.coroutines.flow.Flow

interface IndoLocalAddressRepository {
    suspend fun getProvince(): Flow<List<ProvinceEntity>>
    suspend fun getCities(provinceId: Int): Flow<List<CityEntity>>
    suspend fun getDistricts(provinceId: Int, cityId: Int): Flow<List<DistrictEntity>>
}