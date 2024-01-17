package com.byteteam.bluesense.core.presentation.views.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.data.source.local.room.model.NotificationEntity
import com.byteteam.bluesense.core.domain.repositories.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    private var _notifications: MutableStateFlow<Resource<List<NotificationEntity>>> = MutableStateFlow(Resource.Loading())
    val notifications: StateFlow<Resource<List<NotificationEntity>>> = _notifications
    private var _isDeleteModalOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDeleteModalOpen: StateFlow<Boolean> = _isDeleteModalOpen
    private var _onDelete: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val onDelete: StateFlow<Boolean> = _onDelete

    fun openDeleteModal(){
        _isDeleteModalOpen.value = true
    }

    fun closeDeleteModal(){
        _isDeleteModalOpen.value = false
    }

    fun getNotificationsCurrentUser(){
        _notifications.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                notificationRepository.getNotificationsFromCurrentUser().catch {
                    _notifications.value = Resource.Error(it.message ?: "Error saat mengambil data notifikasi")
                }.collect{
                    _notifications.value = Resource.Success(it)
                }
            }catch (e: Exception){
                _notifications.value = Resource.Error(e.message ?: "Error saat mengambil data notifikasi")
            }
        }
    }

    fun deleteNotificationsCurrentUser(){
        _onDelete.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _notifications.value = Resource.Loading()
            try {
                notificationRepository.deleteNotificationFromCurrentUser()
            }catch (e: Exception){
                _notifications.value = Resource.Error(e.message ?: "Error saat mengambil data notifikasi")
            }finally {
                _onDelete.value = false
            }
        }
    }
}