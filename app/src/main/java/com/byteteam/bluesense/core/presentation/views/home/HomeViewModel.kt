package com.byteteam.bluesense.core.presentation.views.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.DeviceEntity
import com.byteteam.bluesense.core.domain.repositories.DeviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository
) : ViewModel() {
    private var _devices: MutableStateFlow<Resource<List<DeviceEntity>>> = MutableStateFlow(Resource.Loading())
    val devices: StateFlow<Resource<List<DeviceEntity>>> = _devices

    fun getDevices(){
        viewModelScope.launch {
            _devices.value = Resource.Loading()
            try {
                deviceRepository.getDevices().catch {
                    _devices.value = Resource.Error(it.message ?: "Error fetching device data")
                }.collect{
                    _devices.value = Resource.Success(it)
                }
            }catch (e: Exception){
                    _devices.value = Resource.Error(e.message.toString())
            }
        }
    }
}