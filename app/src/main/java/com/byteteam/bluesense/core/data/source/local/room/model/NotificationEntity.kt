package com.byteteam.bluesense.core.data.source.local.room.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.UUID


@Parcelize
@Entity(tableName = "notification")
data class NotificationEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.TEXT)
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "user_id", typeAffinity = ColumnInfo.TEXT)
    val userId: String,

    @ColumnInfo(name = "title", typeAffinity = ColumnInfo.TEXT)
    val title: String? = null,

    @ColumnInfo(name = "body", typeAffinity = ColumnInfo.TEXT)
    val body: String? = null,

    @ColumnInfo(name = "created_at", typeAffinity = ColumnInfo.TEXT)
    val createdAt: String,
) : Parcelable