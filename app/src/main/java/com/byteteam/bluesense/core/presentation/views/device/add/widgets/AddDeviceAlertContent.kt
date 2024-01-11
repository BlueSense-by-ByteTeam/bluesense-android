package com.byteteam.bluesense.core.presentation.views.device.add.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R


@Composable
fun AddDeviceAlertContent(
    onScan: () -> Unit,
    onManual: () -> Unit,
){
    Column(modifier = Modifier
        .height(412.dp)
        .padding(start=24.dp, top=24.dp, end=24.dp, bottom = 48.dp)
        , horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.maintenance_amico), contentDescription = null, modifier = Modifier.size(202.dp))
        Text(text = "Tambah Alat", style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        Text(text = "Pilih metode yang membuat kamu nyaman.", textAlign = TextAlign.Center)
        Spacer(Modifier.weight(1f))
        Button(onClick = onScan, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
            Text(text = "Pindai Kode")
        }
        OutlinedButton(onClick = onManual, border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary), modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
            Text(text = "Tambah Manual")
        }
    }
}