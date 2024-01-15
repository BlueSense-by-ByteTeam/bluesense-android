package com.byteteam.bluesense.core.data.repositories

import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.data.remote.network.services.bluesense.WaterFilterServices
import com.byteteam.bluesense.core.data.remote.network.services.bluesense.WaterSupplierServices
import com.byteteam.bluesense.core.domain.model.WaterFilterEntity
import com.byteteam.bluesense.core.domain.model.WaterSupplierEntity
import com.byteteam.bluesense.core.domain.repositories.StoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val dataStorePreference: DataStorePreference,
    private val waterSupplierServices: WaterSupplierServices,
    private val waterFilterServices: WaterFilterServices
) : StoreRepository{
    override suspend fun getWaterSuppliers(): Flow<Resource<List<WaterSupplierEntity>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getWaterFilters(): Flow<Resource<List<WaterFilterEntity>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFeaturedWaterFilter(): Flow<Resource<WaterFilterEntity>> {
        TODO("Not yet implemented")
    }
}