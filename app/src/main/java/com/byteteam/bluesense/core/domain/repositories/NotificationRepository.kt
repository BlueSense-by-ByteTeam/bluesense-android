package com.byteteam.bluesense.core.domain.repositories

import com.byteteam.bluesense.core.data.source.local.room.model.NotificationEntity
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun insertNewNotification(notificationEntity: NotificationEntity)
    suspend fun deleteNotificationFromCurrentUser()
    suspend fun getNotificationsFromCurrentUser(): Flow<List<NotificationEntity>>
}