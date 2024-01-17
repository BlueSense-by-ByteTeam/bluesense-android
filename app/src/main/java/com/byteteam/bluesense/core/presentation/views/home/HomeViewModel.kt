package com.byteteam.bluesense.core.presentation.views.home

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.DeviceEntity
import com.byteteam.bluesense.core.domain.model.DeviceLatestInfoEntity
import com.byteteam.bluesense.core.domain.repositories.DeviceRepository
import com.byteteam.bluesense.core.services.FCMService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class HomeViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {
    private var _devices: MutableStateFlow<Resource<List<DeviceEntity>>> =
        MutableStateFlow(Resource.Loading())
    val devices: StateFlow<Resource<List<DeviceEntity>>> = _devices

    private var _detailDeviceInfo: MutableStateFlow<Resource<DeviceLatestInfoEntity?>> =
        MutableStateFlow(Resource.Success(null))
    val detailDeviceLatestInfo: StateFlow<Resource<DeviceLatestInfoEntity?>> = _detailDeviceInfo

    fun getDevices() {
        viewModelScope.launch(Dispatchers.IO) {
            _devices.value = Resource.Loading()
            try {
                deviceRepository.getDevices().catch {
                    _devices.value = Resource.Error(it.message ?: "Error fetching device data")
                }.collect {
                    _devices.value = Resource.Success(it)
                    if (it.isNotEmpty()) {
                        getDetailDeviceInfo(it[0].id)
                        FCMService.subscribeTopic(appContext, it[0].macId)
                    }else{
//                        Log.d(TAG, "getDevices: ")
                        resetDetailDeviceInfo()
                    }
                }
            } catch (e: Exception) {
                _devices.value = Resource.Error(e.message.toString())
            }
        }
    }

    private fun resetDetailDeviceInfo(){
        _detailDeviceInfo.value = Resource.Success(null)
    }

    private fun getDetailDeviceInfo(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _detailDeviceInfo.value = Resource.Loading()
            try {
                deviceRepository.getLatestDeviceDetail(id).catch {
                    _detailDeviceInfo.value =
                        Resource.Error(it.message ?: "Error fetching latest device data")
                }.collect {
                    _detailDeviceInfo.value = Resource.Success(it)
                }
            } catch (e: Exception) {
                _detailDeviceInfo.value = Resource.Error(e.message.toString())
            }
        }
    }
}