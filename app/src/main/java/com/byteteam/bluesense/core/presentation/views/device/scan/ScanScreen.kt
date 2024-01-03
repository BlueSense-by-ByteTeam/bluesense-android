package com.byteteam.bluesense.core.presentation.views.device.scan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@Composable
fun ScanScreen(
    modifier: Modifier = Modifier
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        // Adjust the alpha (transparency) for the middle section
        Box(
            modifier = Modifier
                .width(260.dp)
                .height(260.dp)
                .graphicsLayer(alpha = 0.5f) // Set the alpha value here
        ) {
            // Content for the middle section goes here
            // You can add other Composables, text, images, etc.
        }
    }
}


@Preview(device = Devices.PIXEL_4, showBackground = true)
@Composable
private fun Preview(){
    BlueSenseTheme {
        Surface {
            ScanScreen()
        }
    }
}