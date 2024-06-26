package com.byteteam.bluesense.core.data.repositories

import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.data.source.remote.response.water_supplier_detail.WaterSupplierDetailResponse
import com.byteteam.bluesense.core.data.source.remote.services.bluesense.WaterFilterServices
import com.byteteam.bluesense.core.data.source.remote.services.bluesense.WaterSupplierServices
import com.byteteam.bluesense.core.domain.model.WaterFilterEntity
import com.byteteam.bluesense.core.domain.model.WaterSupplierEntity
import com.byteteam.bluesense.core.domain.repositories.StoreRepository
import com.byteteam.bluesense.core.helper.toWaterFilterEntities
import com.byteteam.bluesense.core.helper.toWaterSupplierEntities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val dataStorePreference: DataStorePreference,
    private val waterSupplierServices: WaterSupplierServices,
    private val waterFilterServices: WaterFilterServices
) : StoreRepository {
    override suspend fun getWaterSuppliers(): Flow<Resource<List<WaterSupplierEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val token = dataStorePreference.getAuthToken()
            val response = waterSupplierServices.getWaterSuppliers("Bearer $token")
            emit(Resource.Success(response.toWaterSupplierEntities()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error saat mengambil data supplier air!"))
        }
    }

    override suspend fun getWaterFilters(): Flow<Resource<List<WaterFilterEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val token = dataStorePreference.getAuthToken()
            val response = waterFilterServices.getWaterFilters("Bearer $token")
            emit(Resource.Success(response.toWaterFilterEntities()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error saat mengambil data produk filter air!"))
        }
    }

    override suspend fun getFeaturedWaterFilter(): Flow<Resource<WaterFilterEntity>> = flow {
        emit(Resource.Loading())
        try {
            val token = dataStorePreference.getAuthToken()
            val response = waterFilterServices.getFeaturedWaterFilter("Bearer $token")
            emit(Resource.Success(response.toWaterFilterEntities().get(0)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error saat mengambil data produk filter air!"))
        }
    }

    override suspend fun getWaterSupplierDetail(id: String): Flow<Resource<WaterSupplierDetailResponse>> =  flow {
        emit(Resource.Loading())
        try {
            val token = dataStorePreference.getAuthToken()
            val response = waterSupplierServices.getWaterSupplier("Bearer $token", id)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error saat mengambil detail supplier"))
        }
    }
}