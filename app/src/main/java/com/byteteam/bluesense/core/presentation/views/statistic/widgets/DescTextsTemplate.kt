package com.byteteam.bluesense.core.presentation.views.statistic.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DescTextTemplate(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = 24.dp)) {

        Text(
            text = "Kualitas Air",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
        )
        Text(text = "Kualitas air, diukur melalui parameter Total Dissolved Solids (TDS) dan pH, memberikan gambaran holistik tentang kebersihan dan keberlanjutan air yang dikonsumsi.Air dengan TDS rendah (0-150 ppm) dan pH sekitar 7 dianggap berkualitas baik dan sesuai untuk konsumsi manusia. Kualitas air ini cenderung bersih, tanpa mengandung kontaminan yang signifikan dan memiliki rasa yang netral. Sebaliknya, nilai TDS tinggi (>300 ppm) dan perubahan ekstrem pada pH dapat mengindikasikan kualitas air yang kurang baik. Air dengan TDS tinggi dapat mencakup berbagai kontaminan yang dapat memengaruhi rasa dan kesehatan air.")
    }
}