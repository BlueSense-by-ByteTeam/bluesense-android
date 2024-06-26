package com.byteteam.bluesense.core.presentation.views.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteteam.bluesense.core.data.event.SingleEvent
import com.byteteam.bluesense.core.domain.model.InputData
import com.byteteam.bluesense.core.domain.model.UserData
import com.byteteam.bluesense.core.domain.repositories.AuthRepository
import com.byteteam.bluesense.core.presentation.tokens.EMAIL_REGEX
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {
    val name: MutableStateFlow<InputData> =
        MutableStateFlow(InputData(data = "", errorMessage = null))
    val email: MutableStateFlow<InputData> =
        MutableStateFlow(InputData(data = "", errorMessage = null))
    val password: MutableStateFlow<InputData> =
        MutableStateFlow(InputData(data = "", errorMessage = null))
    val confirmPassword: MutableStateFlow<InputData> =
        MutableStateFlow(InputData(data = "", errorMessage = null))
    val buttonEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val errorEvetMessage = Channel<SingleEvent>()
    val errorEventFlow = errorEvetMessage.receiveAsFlow()

    private val successEvetMessage = Channel<SingleEvent>()
    val successEventFlow = successEvetMessage.receiveAsFlow()

    fun resetState() {
        name.value =
            InputData(data = "", errorMessage = null)
        email.value =
            InputData(data = "", errorMessage = null)
        password.value =
            InputData(data = "", errorMessage = null)
        confirmPassword.value =
            InputData(data = "", errorMessage = null)
        buttonEnabled.value = false
    }

    fun updateName(value: String) {
        val data = InputData(value).copy(
            data = value,
            errorMessage = if (value == "") "Nama tidak boleh kosong" else null
        )
        name.value = data
        updateButtonEnabled()
    }

    fun updateEmail(value: String) {
        val data = InputData(value).copy(
            data = value,
            errorMessage = when {
                value == "" -> "Email tidak boleh kosong"
                !value.matches(EMAIL_REGEX.toRegex()) -> "Format input email salah!"
                else -> null
            }
        )
        email.value = data
        updateButtonEnabled()
    }

    fun updatePassword(value: String) {
        val data = InputData(value).copy(
            data = value,
            errorMessage = when {
                value == "" -> "Password tidak boleh kosong!"
                value.length < 6 -> "Password minimal harus 6 karakter!"
                else -> null
            }
        )
        password.value = data
        updateButtonEnabled()
    }

    fun updateConfirmPassword(value: String) {
        val data = InputData(value).copy(
            data = value,
            errorMessage = when {
                value != password.value.data -> "Konfirmasi password tidak sama,"
                else -> null
            }
        )
        confirmPassword.value = data
        updateButtonEnabled()
    }

    private fun updateButtonEnabled() {
        buttonEnabled.value =
            name.value.errorMessage == null && email.value.errorMessage == null && password.value.errorMessage == null && confirmPassword.value.errorMessage == null
    }

    fun register(callbackOnSuccess: () -> Unit) {
        buttonEnabled.value = false
        viewModelScope.launch {
            authRepository.signUpEmail(name.value.data, email.value.data, password.value.data)
                .catch {
                    Log.d("TAG", "register: ${it.message}")
                }.collect {
                val gson = Gson()
                val json = gson.toJson(it)
                Log.d("TAG", "onCreate: signed user $json")
                it?.errorMessage?.let {
                    errorEvetMessage.send(SingleEvent.MessageEvent(it))
                }
                buttonEnabled.value = true
                if (it?.data != null) callbackOnSuccess()
            }
        }
    }

    fun googleSignup(signUpData: UserData) {
        viewModelScope.launch {
            authRepository.signupGoogle(signUpData.userName, signUpData.email)
        }
    }
}