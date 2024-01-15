package com.byteteam.bluesense.core.presentation.views.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.WaterFilterEntity
import com.byteteam.bluesense.core.domain.model.WaterSupplierEntity
import com.byteteam.bluesense.core.domain.repositories.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val storeRepository: StoreRepository
) : ViewModel(){
    private var _waterSuppliers: MutableStateFlow<Resource<List<WaterSupplierEntity>>> = MutableStateFlow(Resource.Loading())
    private var _waterFilters: MutableStateFlow<Resource<List<WaterFilterEntity>>> = MutableStateFlow(Resource.Loading())
    private var _featuredWaterFilter: MutableStateFlow<Resource<WaterFilterEntity>> = MutableStateFlow(Resource.Loading())

    val waterSuppliers: StateFlow<Resource<List<WaterSupplierEntity>>> = _waterSuppliers
    val waterFilters: StateFlow<Resource<List<WaterFilterEntity>>> = _waterFilters
    val featuredWaterFilter: StateFlow<Resource<WaterFilterEntity>> = _featuredWaterFilter

    fun getWaterSuppliers(){
        viewModelScope.launch {
            try {
                storeRepository.getWaterSuppliers().catch {
                    _waterSuppliers.value = Resource.Error(it.message ?: "Error saat mengambil data supplier air!")
                }.collect{
                    _waterSuppliers.value = it
                }
            }catch (e: Exception){
                _waterSuppliers.value = Resource.Error(e.message ?: "Error saat mengambil data supplier air!")
            }
        }
    }
    fun getWaterFilters(){
        viewModelScope.launch {
            try {
                storeRepository.getWaterFilters().catch {
                    _waterFilters.value = Resource.Error(it.message ?: "Error saat mengambil data supplier air!")
                }.collect{
                    _waterFilters.value = it
                }
            }catch (e: Exception){
                _waterFilters.value = Resource.Error(e.message ?: "Error saat mengambil data filter air!")
            }
        }
    }

    fun getFeaturedWaterFilters(){
        viewModelScope.launch {
            try {
                storeRepository.getFeaturedWaterFilter().catch {
                    _featuredWaterFilter.value = Resource.Error(it.message ?: "Error saat mengambil data supplier air!")
                }.collect{
                    _featuredWaterFilter.value = it
                }
            }catch (e: Exception){
                _featuredWaterFilter.value = Resource.Error(e.message ?: "Error saat mengambil data filter air!")
            }
        }
    }
}