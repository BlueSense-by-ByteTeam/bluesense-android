package com.byteteam.bluesense.core.presentation.widgets

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
fun SuccessAlertContent(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    confirmText: String = "Tutup",
){
    Column(modifier = Modifier
        .height(412.dp)
        .padding(start=24.dp, top=24.dp, end=24.dp, bottom = 48.dp)
        , horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.ok_amico), contentDescription = null, modifier = Modifier.size(202.dp))
        Text(text = title, style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        Text(text = message, textAlign = TextAlign.Center)
        Spacer(Modifier.weight(1f))
        Button(onClick = onConfirm, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
            Text(text = confirmText)
        }
    }
}