package com.byteteam.bluesense.core.presentation.views.device.add_form

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteteam.bluesense.core.data.event.SingleEvent
import com.byteteam.bluesense.core.domain.model.DevicePost
import com.byteteam.bluesense.core.domain.model.InputData
import com.byteteam.bluesense.core.domain.repositories.DeviceRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDeviceFormViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository
) : ViewModel() {
    val name: MutableStateFlow<InputData> = MutableStateFlow(InputData(""))
    val id: MutableStateFlow<InputData> = MutableStateFlow(InputData(""))
    val province: MutableStateFlow<InputData> = MutableStateFlow(InputData(""))
    val city: MutableStateFlow<InputData> = MutableStateFlow(InputData(""))
    val district: MutableStateFlow<InputData> = MutableStateFlow(InputData(""))
    val address: MutableStateFlow<InputData> = MutableStateFlow(InputData(""))
    val waterSource: MutableStateFlow<InputData> = MutableStateFlow(InputData(""))

    private var _buttonEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val buttonEnabled: StateFlow<Boolean> = _buttonEnabled


    private val eventChannel = Channel<SingleEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun updateName(value: String) {
        val data = InputData(value).copy(
            data = value,
            errorMessage = when {
                value == "" -> "Nama tidak boleh kosong!"
                else -> null
            }
        )
        name.value = data
        updateButtonEnabled()
    }

    fun updateId(value: String) {
        val data = InputData(value).copy(
            data = value,
            errorMessage = when {
                value == "" -> "Id device tidak boleh kosong!"
                else -> null
            }
        )
        id.value = data
        updateButtonEnabled()
    }

    fun updateCity(value: String) {
        val data = InputData(value).copy(
            data = value,
            errorMessage = when {
                value == "" -> "Kota/Kab tidak boleh kosong!"
                else -> null
            }
        )
        city.value = data
        updateButtonEnabled()
    }

    fun updateProvince(value: String) {
        val data = InputData(value).copy(
            data = value,
            errorMessage = when {
                value == "" -> "Provinsi tidak boleh kosong!"
                else -> null
            }
        )
        province.value = data
        updateButtonEnabled()
    }

    fun updateDistrict(value: String) {
        val data = InputData(value).copy(
            data = value,
            errorMessage = when {
                value == "" -> "Kecamatan tidak boleh kosong!"
                else -> null
            }
        )
        district.value = data
        updateButtonEnabled()
    }

    fun updateAddress(value: String) {
        val data = InputData(value).copy(
            data = value,
            errorMessage = when {
                value == "" -> "Alamat tidak boleh kosong!"
                else -> null
            }
        )
        address.value = data
        updateButtonEnabled()
    }

    fun updateWaterSource(value: String) {
        val data = InputData(value).copy(
            data = value,
            errorMessage = when {
                value == "" -> "Sumber air tidak boleh kosong!"
                else -> null
            }
        )
        waterSource.value = data
        updateButtonEnabled()
    }

    private fun updateButtonEnabled() {
        _buttonEnabled.value =
            name.value.data.isNotEmpty() && id.value.data.isNotEmpty() && province.value.data.isNotEmpty() && city.value.data.isNotEmpty() && district.value.data.isNotEmpty() && address.value.data.isNotEmpty() && waterSource.value.data.isNotEmpty()
    }

    fun postDevice(callbackOnSuccess: () -> Unit = {}) {
        _buttonEnabled.value = false
        viewModelScope.launch {
            try {
                val data = DevicePost(
                    deviceId = id.value.data,
                    name = name.value.data,
                    province = province.value.data,
                    city = city.value.data,
                    district = district.value.data,
                    address = address.value.data,
                    waterSource = waterSource.value.data,
                )
                deviceRepository.postDevice(data).catch {
                    eventChannel.send(SingleEvent.MessageEvent(it.message.toString()))
                }.collect {
                    val gson = Gson()
                    val json = gson.toJson(it)
                    Log.d("TAG", "onCreate: signed user $json")

                    callbackOnSuccess()
                }
            } catch (e: Exception) {
                eventChannel.send(SingleEvent.MessageEvent(e.message.toString()))
            } finally {
                _buttonEnabled.value = true
            }
        }
    }
}