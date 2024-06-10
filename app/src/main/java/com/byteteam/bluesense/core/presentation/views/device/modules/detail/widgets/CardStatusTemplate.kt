package com.byteteam.bluesense.core.presentation.views.device.modules.detail.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.domain.model.SensorData
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CardStatusTemplate(
    sensorData: StateFlow<SensorData?>,
    waterQualityRealtime: StateFlow<String?>,
    waterStatusRealtime: StateFlow<String?>,
    modifier: Modifier = Modifier
) {

    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CardStatus(
                label = "Kualitas Air",
                text = waterQualityRealtime.collectAsState().value.let{
                    when(it){
                        null -> "Buruk"
                        else -> it.capitalize()
                    }
                },
                iconPainter = painterResource(id = R.drawable.ic_water_droplet),
                modifier = Modifier.weight(1f)
            )
            CardStatus(
                label = "Status Air",
                text = waterStatusRealtime.collectAsState().value.let{
                    when(it){
                        "baik" -> "Dapat diminum"
                        else -> "Tidak dapat diminum"
                    }
                },
                iconPainter = painterResource(id = R.drawable.ic_water_quality_vector),
                modifier = Modifier.weight(1f)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CardStatus(
                label = "TDS (PPM)",
                text = (sensorData?.collectAsState()?.value?.tds ?: 0.0).toString(),
                iconPainter = painterResource(id = R.drawable.ic_tds),
                modifier = Modifier.weight(1f)
            )
            CardStatus(
                label = "Level PH)",
                text = (sensorData?.collectAsState()?.value?.ph ?: 0.0).toString(),
                iconPainter = painterResource(id = R.drawable.ic_ph),
                modifier = Modifier.weight(1f)
            )
        }
    }
}