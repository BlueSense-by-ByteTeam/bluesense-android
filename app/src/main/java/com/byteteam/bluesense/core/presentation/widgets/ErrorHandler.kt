package com.byteteam.bluesense.core.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@Composable
fun ErrorHandler(
    onPressRetry: () -> Unit,
    errorText: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.oh_no_amico),
            contentDescription = "error image",
            modifier = Modifier
                .padding(bottom = 12.dp)
                .size(300.dp)
        )
        Text("Ooops!!", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text(text = errorText, textAlign = TextAlign.Center)
        Text(
            text = "Muat ulang",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 20.dp)
                .clickable { onPressRetry() },
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun Preview(){
    BlueSenseTheme {
        Surface {
            ErrorHandler(onPressRetry = { /*TODO*/ }, errorText = "error")
        }
    }
}