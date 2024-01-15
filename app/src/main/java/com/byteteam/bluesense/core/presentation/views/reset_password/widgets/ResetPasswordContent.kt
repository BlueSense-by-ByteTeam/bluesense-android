package com.byteteam.bluesense.core.presentation.views.reset_password.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R

@Composable
fun ResetPasswordContent(){
    Image(
        painter = painterResource(id = R.drawable.security_on_rafiki),
        contentDescription = null,
        modifier = Modifier
            .padding(bottom = 20.dp)
            .size(202.dp)
    )
    Text(
        text = "Lupa kata sandi?",
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = 12.dp)
    )
    Text(
        text = "Masukkan email kamu dan Bluesense akan mengirimkan email instruksi untuk mengatur ulang kata sandi.",
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = 20.dp)
    )
}