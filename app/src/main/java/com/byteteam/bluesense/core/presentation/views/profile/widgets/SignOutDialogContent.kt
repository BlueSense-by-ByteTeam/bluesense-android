package com.byteteam.bluesense.core.presentation.views.profile.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R

@Composable
fun SignOutDialogContent(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
){
    Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Image(
            painter = painterResource(id = R.drawable.escape_pana),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(160.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Yakin ingin keluar dari Bluesense?",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge
        )
        Text(text = "Kamu perlu masuk lagi untuk menggunakan Bluesense kembali", textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom=8.dp),)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(onClick = onDismiss,modifier = Modifier.weight(1f),  shape = RoundedCornerShape(12.dp), border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)) {
                Text(text = "Batal")
            }
            Button(onClick = onConfirm, modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp)) {
                Text(text = "Keluar")
            }
        }
    }
}