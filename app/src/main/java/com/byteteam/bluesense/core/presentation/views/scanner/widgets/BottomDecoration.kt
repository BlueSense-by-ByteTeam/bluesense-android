package com.byteteam.bluesense.core.presentation.views.scanner.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BottomDecoration(modifier: Modifier = Modifier){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Shield,
            tint = Color.White,
            contentDescription = null
        )
        Text(
            text = "Scanned by Google on behalf of Bluesense",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
        Icon(
            imageVector = Icons.Default.Info,
            tint = Color.White,
            contentDescription = null
        )
    }
}