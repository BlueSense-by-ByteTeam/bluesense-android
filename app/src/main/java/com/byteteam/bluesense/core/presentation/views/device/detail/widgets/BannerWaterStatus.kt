package com.byteteam.bluesense.core.presentation.views.device.detail.widgets

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.ui.theme.Yellow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BannerWaterStatus(
    modifier: Modifier = Modifier,
    connected: Boolean,
    waterQualityRealtime: StateFlow<String?>,
    waterQualityHistory: String?
) {
    val waterQualityRealtimeState = waterQualityRealtime.collectAsState().value
    Log.d("TAG", "BannerWaterStatus: $waterQualityHistory")
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                when {
                    waterQualityRealtimeState == "buruk" -> Yellow
                    waterQualityRealtimeState == "baik" -> MaterialTheme.colorScheme.primary
//                    waterQualityHistory == "buruk" -> Yellow
//                    waterQualityHistory == "baik" -> MaterialTheme.colorScheme.primary
                    connected == false -> Color.Red
                    else -> Yellow
                }
            )
            .padding(vertical = 12.dp, horizontal = 20.dp)
    ) {
        if (connected) {

            Log.d("TAG", "BannerWaterStatus: $waterQualityRealtimeState")
            Log.d("TAG", "BannerWaterStatus: $waterQualityHistory")

            Text(
                text = when {
                    waterQualityRealtimeState == "baik" -> "Air Aman!"
                    waterQualityHistory == "baik" -> "Air Aman!"
                    else -> "Air Buruk!"
                }, fontWeight = FontWeight.Bold, color =
                when {
                    waterQualityRealtimeState == "buruk" -> Color.Black
                    waterQualityHistory == "buruk" -> Color.Black
                    waterQualityRealtimeState == null && waterQualityHistory == null -> Color.Black
                    else -> MaterialTheme.colorScheme.onPrimary
                }
            )
            Text(
                text =
                when {
                    waterQualityRealtimeState == "baik" -> "Kualitas air bagus dan dapat diminum."
                    waterQualityHistory == "baik" -> "Kualitas air bagus dan dapat diminum."
                    else -> "Kualitas air buruk dan tidak dapat diminum."
                }, color =
                when {
                    waterQualityRealtimeState == "baik" ->  MaterialTheme.colorScheme.onPrimary
                    waterQualityHistory == "baik" ->  MaterialTheme.colorScheme.onPrimary
                    else -> Color.Black
                }
            )
        } else {
            Text(
                text = "Cek alat deteksi atau hubungkan ulang alat.",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}