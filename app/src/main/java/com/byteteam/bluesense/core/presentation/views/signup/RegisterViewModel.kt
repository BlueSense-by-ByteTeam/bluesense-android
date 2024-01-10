package com.byteteam.bluesense.core.presentation.views.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteteam.bluesense.core.domain.model.SignInResult
import com.byteteam.bluesense.core.domain.repositories.AuthRepository
import com.byteteam.bluesense.core.helper.UiState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    val name: MutableStateFlow<String> = MutableStateFlow("")
    val email: MutableStateFlow<String> = MutableStateFlow("")
    val password: MutableStateFlow<String> = MutableStateFlow("")
    val confirmPassword: MutableStateFlow<String> = MutableStateFlow("")
    val buttonEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private var _signInResult: MutableStateFlow<UiState<SignInResult>> = MutableStateFlow(UiState.Loading)
    fun updateName(value: String){
        name.value = value
        updateButtonEnabled()
    }
    fun updateEmail(value: String){
        email.value = value
        updateButtonEnabled()
    }
    fun updatePassword(value: String){
        password.value = value
        updateButtonEnabled()
    }
    fun updateConfirmPassword(value: String){
        confirmPassword.value = value
        updateButtonEnabled()
    }
    private fun updateButtonEnabled(){
        buttonEnabled.value = name.value.isNotEmpty() && email.value.isNotEmpty() && password.value.isNotEmpty() && confirmPassword.value.isNotEmpty()
    }
    fun register(callbackOnSuccess: () -> Unit){
        viewModelScope.launch {
            authRepository.signUpEmail(name.value, email.value, password.value).catch {  }.collect{
                val gson = Gson()
                val json = gson.toJson(it)
                Log.d("TAG", "onCreate: signed user $json")
                callbackOnSuccess()
            }
        }
    }
}