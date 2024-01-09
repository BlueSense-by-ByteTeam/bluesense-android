package com.byteteam.bluesense.core.presentation.views.home.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R

@Composable
fun HomeTopBar(
    navigateNotificationScreen: () -> Unit = {},
    modifier: Modifier = Modifier){
    Row(modifier.padding(24.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(text = stringResource(R.string.bluesense), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
        IconButton(onClick = navigateNotificationScreen) {
            Icon(
                imageVector = Icons.Default.Notifications, tint = MaterialTheme.colorScheme.primary, contentDescription = stringResource(
                    R.string.icon_notification
                )
            )
        }
    }
}