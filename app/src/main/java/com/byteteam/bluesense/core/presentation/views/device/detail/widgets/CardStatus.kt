package com.byteteam.bluesense.core.presentation.views.device.detail.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.ui.theme.LightBlue

@Composable
fun CardStatus(
    label: String,
    text: String,
    iconVector: ImageVector,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(LightBlue)
            .padding(12.dp)
    ) {
        Icon(
            imageVector = iconVector,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = "water quality ic",
            modifier = Modifier
                .padding(bottom = 12.dp)
                .clip(
                    RoundedCornerShape(4.dp)
                )
                .background(Color.White)
                .padding(8.dp)
        )
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = text, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
    }
}