package com.byteteam.bluesense.core.presentation.views.device.modules.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.data.event.SingleEvent
import com.byteteam.bluesense.core.domain.model.SensorData
import com.byteteam.bluesense.core.domain.repositories.DeviceRepository
import com.byteteam.bluesense.core.presentation.helper.WaterQuality
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
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

    private var _onDelete: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val onDelete: StateFlow<Boolean> = _onDelete


    private val eventChannel = Channel<SingleEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun updateDeviceStatus(status: Boolean) {
        _isConnected.value = status
    }

    fun openDeleteModal() {
        _openDeleteModal.value = true
    }

    fun closeDeleteModal() {
        _openDeleteModal.value = false
    }

    fun deleteDevice(id: String, callbackOnSuccess: () -> Unit) {
        viewModelScope.launch {
            _onDelete.value = true
            try {
                deviceRepository.deleteDevice(id).catch {
                    eventChannel.send(
                        SingleEvent.MessageEvent(
                            it.message ?: "Error delete device data"
                        )
                    )
                }.collect {
                    callbackOnSuccess()
                    reset()
                }
            } catch (e: Exception) {
                eventChannel.send(SingleEvent.MessageEvent(e.message ?: "Error delete device data"))
            } finally {
                _onDelete.value = false
                _openDeleteModal.value = false
            }
        }
    }

    private fun reset() {
        _isConnected.value = false
        _data.value = null
        _waterStatus.value = null
        _waterQuality.value = null
    }

    fun updateDeviceSensorValue(data: SensorData) {
        val detectionResult = WaterQuality.checkWater(data.tds, data.ph)
        _data.value = data
        _waterQuality.value = if (detectionResult.first) "baik" else "buruk"
        _waterStatus.value = if (detectionResult.second) "baik" else "buruk"
    }
}