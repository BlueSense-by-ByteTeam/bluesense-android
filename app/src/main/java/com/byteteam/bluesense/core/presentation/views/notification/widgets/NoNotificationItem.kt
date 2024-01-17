package com.byteteam.bluesense.core.presentation.views.notification.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R

@Composable
fun NoNotificationItem(){
    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(id = R.drawable.push_notifications),
            modifier = Modifier.size(300.dp),
            contentDescription = "no notification data"
        )
        Text(
            text = stringResource(R.string.no_notification),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.no_notification_desc),
            textAlign = TextAlign.Center
        )
    }
}