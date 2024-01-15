package com.byteteam.bluesense.core.presentation.views.device.detail.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R


@Composable
fun DeleteDeviceAlertContent(
    isOnDelete: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
){
    Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Image(
            painter = painterResource(id = R.drawable.trash_1),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(160.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Yakin ingin hapus alat ini?",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge
        )
        Text(text = "Hapus alat akan menghapus semua data.", textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(onClick = onDismiss,modifier = Modifier.weight(1f),  shape = RoundedCornerShape(12.dp), border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)) {
                Text(text = "Batal")
            }
            Button(onClick = {
                if (!isOnDelete) {
                    onConfirm()
                }
            }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp)) {
               if(isOnDelete) CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp, modifier = Modifier.size(12.dp
               )) else Text(text = "Hapus")
            }
        }
    }
}