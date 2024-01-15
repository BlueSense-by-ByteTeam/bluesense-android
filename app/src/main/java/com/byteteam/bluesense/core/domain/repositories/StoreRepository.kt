package com.byteteam.bluesense.core.domain.repositories

import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.WaterFilterEntity
import com.byteteam.bluesense.core.domain.model.WaterSupplierEntity
import kotlinx.coroutines.flow.Flow


interface StoreRepository {
    suspend fun getWaterSuppliers(): Flow<Resource<List<WaterSupplierEntity>>>
    suspend fun getWaterFilters(): Flow<Resource<List<WaterFilterEntity>>>
    suspend fun getFeaturedWaterFilter(): Flow<Resource<WaterFilterEntity>>
}