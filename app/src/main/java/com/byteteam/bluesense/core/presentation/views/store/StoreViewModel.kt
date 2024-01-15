package com.byteteam.bluesense.core.presentation.views.store

import androidx.lifecycle.ViewModel
import com.byteteam.bluesense.core.domain.repositories.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val storeRepository: StoreRepository
) : ViewModel(){
}