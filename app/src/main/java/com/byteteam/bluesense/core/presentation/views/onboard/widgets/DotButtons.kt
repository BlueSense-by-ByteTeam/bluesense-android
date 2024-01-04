package com.byteteam.bluesense.core.presentation.views.onboard.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@Composable
fun DotButtons(activeIndex: Int = 0, callbackOnTap: (Int) -> Unit = {},  modifier: Modifier = Modifier){
    val dotColor = Color(0xFFD9D9D9)

    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = modifier) {
        items(4){
            Box(modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(if(activeIndex == it) Color.Black else dotColor)
                .clickable {
                    callbackOnTap(it)
                }
            )
        }
    }
}

@Preview
@Composable
private fun Preview(){
    BlueSenseTheme {
        Surface {
            DotButtons()
        }
    }
}