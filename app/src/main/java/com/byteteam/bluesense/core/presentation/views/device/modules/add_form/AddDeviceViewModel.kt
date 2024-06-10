package com.byteteam.bluesense.core.presentation.views.device.modules.add_form

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteteam.bluesense.core.domain.model.CityEntity
import com.byteteam.bluesense.core.domain.model.DistrictEntity
import com.byteteam.bluesense.core.domain.model.ProvinceEntity
import com.byteteam.bluesense.core.domain.repositories.IndoLocalAddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDeviceViewModel @Inject constructor(
    private val indoLocalAddressRepository: IndoLocalAddressRepository
) : ViewModel() {
    private var _province: MutableStateFlow<List<ProvinceEntity>> = MutableStateFlow(listOf())
    private var _cities: MutableStateFlow<List<CityEntity>> = MutableStateFlow(listOf())
    private var _districts: MutableStateFlow<List<DistrictEntity>> = MutableStateFlow(listOf())

    val province: StateFlow<List<ProvinceEntity>> = _province
    val cities: StateFlow<List<CityEntity>> = _cities
    val districts: StateFlow<List<DistrictEntity>> = _districts

    init{
        getProvinces()
    }

    fun getProvinces(){
        viewModelScope.launch {
            indoLocalAddressRepository.getProvince().catch {
                Log.d("TAG", "getProvinces: $it")
            }.collect{
                _province.value = it
            }
        }
    }

    fun getCities(id: Int){
        viewModelScope.launch {
            indoLocalAddressRepository.getCities(id).catch {
                Log.d("TAG", "get cities: $it")
            }.collect{
                _cities.value = it
            }
        }
    }

    fun getDistricts(provinceId: Int, cityId: Int){
        viewModelScope.launch {
            indoLocalAddressRepository.getDistricts(provinceId, cityId).catch {
                Log.d("TAG", "getDistricts: $it")
            }.collect{
                _districts.value = it
            }
        }
    }


}