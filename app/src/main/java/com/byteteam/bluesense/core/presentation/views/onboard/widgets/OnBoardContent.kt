package com.byteteam.bluesense.core.presentation.views.onboard.widgets

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.presentation.views.onboard.common.OnBoardPageContent
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@Composable
fun OnBoardContent(
    onBoardPageContent: OnBoardPageContent,
    modifier: Modifier = Modifier
){
    Column(modifier.fillMaxSize().padding(start = 24.dp, end = 24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Image(painter = onBoardPageContent.imagePainter, contentDescription = onBoardPageContent.desc, modifier = Modifier.size(336.dp))
        Text(text = onBoardPageContent.title, modifier = Modifier.padding(top = 4.dp), style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
        Text(text = onBoardPageContent.textBody, modifier = Modifier.padding(top = 20.dp), style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
    }
}

@Preview
@Composable
private fun Preview(){
    BlueSenseTheme {
        val onBoardPageContent = OnBoardPageContent(
            imagePainter = painterResource(id = R.drawable.water_drop_rafiki_1), desc ="page-1", textBody =  "Bluesense akan memberikan informasi real-time untuk memastikan air di sekitarmu selalu bersih dan aman.", title = "Mulai deteksi kualitas air secara langsung!"
        )
        Surface {
            OnBoardContent(onBoardPageContent)
        }
    }
}