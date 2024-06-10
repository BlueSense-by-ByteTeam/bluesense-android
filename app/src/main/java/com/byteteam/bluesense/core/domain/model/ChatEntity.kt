package com.byteteam.bluesense.core.domain.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.util.Date

data class ChatEntity(
    val text: String,
    val created: LocalDateTime,
    val isMe: Boolean
)
