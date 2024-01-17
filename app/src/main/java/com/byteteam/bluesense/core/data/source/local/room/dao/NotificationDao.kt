package com.byteteam.bluesense.core.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.byteteam.bluesense.core.data.source.local.room.model.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Insert
    fun insert(notification: NotificationEntity) : Long
    @Query("DELETE FROM notification WHERE user_id=:userId")
    fun deleteAll(userId: String): Int
    @Query("SELECT * FROM notification WHERE user_id=:userId")
    fun getNotificationsCurrentUser(userId: String): Flow<List<NotificationEntity>>
}