package com.byteteam.bluesense.core.presentation.views.scanner.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ScannerActions(
    onNavBack: () -> Unit,
    onToggleFlash: () -> Unit,
    isFlashOn: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable { onNavBack() }
            .padding(4.dp)) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
        Text(text = "Scan Code", color = Color.White)
        Box(modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable { onToggleFlash() }
            .padding(4.dp)) {
            Icon(
                imageVector = if (isFlashOn) Icons.Default.FlashOff else Icons.Default.FlashOn,
                tint = Color.Gray,
                contentDescription = null
            )
        }
    }
}