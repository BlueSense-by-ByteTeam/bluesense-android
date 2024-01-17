package com.byteteam.bluesense.core.presentation.views.notification.widgets

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.core.data.source.local.room.model.NotificationEntity
import com.byteteam.bluesense.core.helper.formatUIDate
import java.time.LocalDateTime

@Composable
fun NotificationItem(
    notificationEntity: NotificationEntity
){
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            tint = Color.White,
            contentDescription = null,
            modifier = Modifier
                .clip(
                    CircleShape
                )
                .background(MaterialTheme.colorScheme.primary)
                .padding(4.dp)
        )
        Column(Modifier.weight(1f)) {
            Text(
                text = notificationEntity.title ?: "Air Buruk!",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = notificationEntity.body ?: "Penting! Kualitas air saat ini menunjukkan tingkat yang tidak memadai.",
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Text(text = notificationEntity.createdAt.formatUIDate(), color = Color.Black)
    }
}