package com.byteteam.bluesense.core.presentation.views.device.detail

import androidx.lifecycle.ViewModel
import com.byteteam.bluesense.core.domain.model.SensorData
import com.byteteam.bluesense.core.domain.repositories.DeviceRepository
import com.byteteam.bluesense.core.presentation.helper.WaterQuality
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetailDeviceViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository
) : ViewModel() {
    private var _isConnected: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var _data: MutableStateFlow<SensorData?> = MutableStateFlow(null)
    private var _waterStatus: MutableStateFlow<String?> = MutableStateFlow(null)
    private var _waterQuality: MutableStateFlow<String?> = MutableStateFlow(null)

    val isConnected: StateFlow<Boolean> = _isConnected
    val data: StateFlow<SensorData?> = _data
    val waterStatus: StateFlow<String?> = _waterStatus
    val waterQuality: StateFlow<String?> = _waterQuality

    private var _openDeleteModal: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDialogDeleteOpen: StateFlow<Boolean> = _openDeleteModal
    
    fun updateDeviceStatus(status: Boolean){
        _isConnected.value = status
    }
    
    fun openDeleteModal(){
        _openDeleteModal.value = true
    }
    
    fun closeDeleteModal(){
        _openDeleteModal.value = false
    }
    
    fun deleteDevice(){
        // TODO: add delete feature
        _openDeleteModal.value = false
    }

    fun updateDeviceSensorValue(data: SensorData){
        val detectionResult = WaterQuality.checkWater(data.tds, data.ph)
        _data.value = data
        _waterQuality.value = if(detectionResult.first) "baik" else "buruk"
        _waterStatus.value = if(detectionResult.second) "baik" else "buruk"
    }
}