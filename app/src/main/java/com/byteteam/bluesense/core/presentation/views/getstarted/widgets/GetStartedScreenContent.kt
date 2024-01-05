package com.byteteam.bluesense.core.presentation.views.getstarted.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R

@Composable
fun GetStartedScreenContent(modifier: Modifier = Modifier){
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = modifier.fillMaxSize().padding(horizontal = 24.dp)) {
        Image(painter = painterResource(id = R.drawable.hydratation), contentDescription = stringResource(
            R.string.get_started_image), modifier = Modifier.size(336.dp))
        Text(text = "Ayo mulai cek kualitas airmu dengan Bluesense sekarang!", modifier = Modifier.padding(top = 4.dp, bottom = 48.dp), style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
        Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.enter))
        }
        OutlinedButton(onClick = { /*TODO*/ }, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth(), border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary)) {
            Text(text = stringResource(R.string.dont_have_account))
        }
    }
}