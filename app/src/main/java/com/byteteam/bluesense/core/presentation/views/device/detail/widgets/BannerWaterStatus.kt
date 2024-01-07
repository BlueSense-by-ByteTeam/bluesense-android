package com.byteteam.bluesense.core.presentation.views.device.detail.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BannerWaterStatus(modifier: Modifier = Modifier){
    Column(modifier = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(MaterialTheme.colorScheme.primary)
        .padding(vertical = 12.dp, horizontal = 20.dp)) {
        Text(text = "Air aman!", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
        Text(text = "Kualitas air bagus dan dapat diminum.", color = MaterialTheme.colorScheme.onPrimary)
    }
}