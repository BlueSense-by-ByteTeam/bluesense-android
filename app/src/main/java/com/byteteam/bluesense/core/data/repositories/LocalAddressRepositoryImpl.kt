package com.byteteam.bluesense.core.data.repositories

import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.data.remote.network.config.APIClient
import com.byteteam.bluesense.core.domain.model.CityEntity
import com.byteteam.bluesense.core.domain.model.DistrictEntity
import com.byteteam.bluesense.core.domain.model.ProvinceEntity
import com.byteteam.bluesense.core.domain.repositories.LocalAddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalAddressRepositoryImpl : LocalAddressRepository {
    val localAddressApi = APIClient.clientLocalAddress()
    override fun getProvince(): Flow<Resource<List<ProvinceEntity>>> = flow {
        emit(Resource.Loading())
        try {

        }catch (e: Exception){

        }
    }

    override fun getCities(provinceId: Int): Flow<Resource<List<CityEntity>>> {
        TODO("Not yet implemented")
    }

    override fun getDistricts(cityId: Int): Flow<Resource<List<DistrictEntity>>> {
        TODO("Not yet implemented")
    }
}