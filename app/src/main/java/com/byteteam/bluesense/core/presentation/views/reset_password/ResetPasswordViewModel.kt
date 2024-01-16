package com.byteteam.bluesense.core.presentation.views.reset_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteteam.bluesense.core.data.event.SingleEvent
import com.byteteam.bluesense.core.domain.model.InputData
import com.byteteam.bluesense.core.domain.repositories.AuthRepository
import com.byteteam.bluesense.core.presentation.tokens.EMAIL_REGEX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(){
    val inputEmail: MutableStateFlow<InputData> = MutableStateFlow(InputData(""))
    val buttonEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val eventChannel = Channel<SingleEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun resetState(){
        inputEmail.value = InputData("")
        buttonEnabled.value = false
    }
    fun updateEmail(value: String){
        viewModelScope.launch {
            val data = InputData(value).copy(
                data = value,
                errorMessage = when {
                    value == "" -> "Email tidak boleh kosong"
                    !value.matches(EMAIL_REGEX.toRegex()) -> "Format input email salah!"
                    else -> null
                }
            )
            inputEmail.value = data
            updateEnableButton()
        }
    }

    private fun updateEnableButton(){
        buttonEnabled.value = inputEmail.value.data.isNotEmpty() && inputEmail.value.errorMessage == null
    }

    fun sendEmailResetPassword(callbackSuccess: () -> Unit){
        viewModelScope.launch {
            buttonEnabled.value = false
            try {
                authRepository.forgotPassword(inputEmail.value.data).catch {
                    eventChannel.send(SingleEvent.MessageEvent(it.message ?: "Error sending email reset password!"))
                }.collect{
                    callbackSuccess()
                }
            }catch (e: Exception){
                eventChannel.send(SingleEvent.MessageEvent(e.message ?: "Error sending email reset password!"))
            }finally {
                buttonEnabled.value = true
            }
        }
    }
}