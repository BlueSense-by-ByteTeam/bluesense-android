package com.byteteam.bluesense.core.data.repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.byteteam.bluesense.core.data.datastore.DataStorePreference
import com.byteteam.bluesense.core.data.source.local.room.dao.NotificationDao
import com.byteteam.bluesense.core.data.source.local.room.model.NotificationEntity
import com.byteteam.bluesense.core.domain.repositories.NotificationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStorePreference: DataStorePreference,
    private val notificationDao: NotificationDao,
) : NotificationRepository{
    override fun insertNewNotification(notificationEntity: NotificationEntity) {
       try {
           CoroutineScope(Dispatchers.IO).launch{
               val userId = dataStorePreference.getUserId().first()
               Log.d("TAG", "insertNewNotification: current user id $userId")
               val data = notificationEntity.copy(userId=userId)
               notificationDao.insert(data)
           }
       }catch (e: Exception){
            Toast.makeText(context, "Data notifikasi gagal disimpan", Toast.LENGTH_SHORT).show()
       }
    }

    override suspend fun deleteNotificationFromCurrentUser() {
        try {
            CoroutineScope(Dispatchers.IO).launch{
                val userId = dataStorePreference.getUserId().first()
                Log.d("TAG", "insertNewNotification: current user id $userId")
                notificationDao.deleteAll(userId)
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getNotificationsFromCurrentUser(): Flow<List<NotificationEntity>> {
        try {
            val userId = dataStorePreference.getUserId().first()
            Log.d("TAG", "insertNewNotification: current user id $userId")
            return notificationDao.getNotificationsCurrentUser(userId)
        }catch (e: Exception){
            throw e
        }
    }
}