package com.byteteam.bluesense.core.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.byteteam.bluesense.core.data.source.local.room.model.NotificationEntity

@Dao
interface NotificationDao {
//    @Insert
//    suspend fun insert(notification: NotificationEntity) : Long
//    @Query("DELETE FROM notification WHERE user_id=:id")
//    fun deleteAll(id: String):Int
//    @Query("SELECT * FROM notification WHERE user_id=:id")
//    suspend fun getHistory(id: String): List<NotificationEntity>
}