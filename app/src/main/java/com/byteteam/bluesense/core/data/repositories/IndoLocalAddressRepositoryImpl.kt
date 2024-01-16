package com.byteteam.bluesense.core.data.repositories

import com.byteteam.bluesense.core.data.source.remote.services.location.IndonesianLocationAddressService
import com.byteteam.bluesense.core.domain.model.CityEntity
import com.byteteam.bluesense.core.domain.model.DistrictEntity
import com.byteteam.bluesense.core.domain.model.ProvinceEntity
import com.byteteam.bluesense.core.domain.repositories.IndoLocalAddressRepository
import com.byteteam.bluesense.core.helper.toCitiesEntity
import com.byteteam.bluesense.core.helper.toDistrictEntity
import com.byteteam.bluesense.core.helper.toProvinceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IndoLocalAddressRepositoryImpl @Inject constructor(
    private val localAddressServices: IndonesianLocationAddressService,
) : IndoLocalAddressRepository {
    override suspend fun getProvince(): Flow<List<ProvinceEntity>> = flow {
        try {
            val result = localAddressServices.getProvinces().map { it.toProvinceEntity() }
            emit(result)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCities(provinceId: Int): Flow<List<CityEntity>> = flow {
        try {
            val result = localAddressServices.getCities(provinceId).map { it.toCitiesEntity() }
            emit(result)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getDistricts(provinceId: Int, cityId: Int): Flow<List<DistrictEntity>> = flow {
        try {
            val result = localAddressServices.getDistricts(provinceId, cityId).map { it.toDistrictEntity() }
            emit(result)
        } catch (e: Exception) {
            throw e
        }
    }
}