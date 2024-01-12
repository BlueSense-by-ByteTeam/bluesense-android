package com.byteteam.bluesense.core.presentation.views.device.detail.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.core.domain.model.SensorData
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CardStatusTemplate(sensorData: StateFlow<SensorData?>, modifier: Modifier = Modifier) {

    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CardStatus(
                label = "Kualitas Air",
                text = "Baik",
                iconVector = Icons.Default.WaterDrop,
                modifier = Modifier.weight(1f)
            )
            CardStatus(
                label = "Status Air",
                text = "Dapat diminum",
                iconVector = Icons.Default.WaterDrop,
                modifier = Modifier.weight(1f)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CardStatus(
                label = "TDS (PPM)",
                text = (sensorData?.collectAsState()?.value?.tds ?: 0.0).toString(),
                iconVector = Icons.Default.WaterDrop,
                modifier = Modifier.weight(1f)
            )
            CardStatus(
                label = "Level PH)",
                text = (sensorData?.collectAsState()?.value?.ph ?: 0.0).toString(),
                iconVector = Icons.Default.WaterDrop,
                modifier = Modifier.weight(1f)
            )
        }
    }
}