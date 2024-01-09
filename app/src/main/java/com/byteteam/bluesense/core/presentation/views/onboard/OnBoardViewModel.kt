package com.byteteam.bluesense.core.presentation.views.onboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteteam.bluesense.core.domain.repositories.OnBoardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val onBoardingRepository: OnBoardingRepository
): ViewModel(){
    private var _isFirstTimeOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFirstTimeOpen: StateFlow<Boolean> = _isFirstTimeOpen
    init {
        checkFirstTimeOpen()
    }
    private fun checkFirstTimeOpen(){
        viewModelScope.launch {
            onBoardingRepository.checkIsFirstTimeOpen().collect{
                _isFirstTimeOpen.value = it
            }
        }
    }

    fun finishOnBoarding(){
        viewModelScope.launch {
            onBoardingRepository.finishOnBoarding()
        }
    }
}