package com.byteteam.bluesense.core.presentation.views.home.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@Composable
fun BlueBuddyChatBotBanner(
    navigateToChatBotScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFE4F1FF))
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
    ) {
        Row {
            Box(Modifier.align(Alignment.Bottom).height(120.dp).width(120.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.chat_bot_small),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    alignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 16.dp, horizontal = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.blue_buddy), style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = stringResource(R.string.chat_bot_banner_desc),
                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.SemiBold),
                    modifier = Modifier
                        .padding(
                            top
                            = 4.dp
                        ),
                )
                Button(
                    onClick = navigateToChatBotScreen, modifier = Modifier.padding(
                        top
                        = 8.dp
                    )
                ) {
                    Text(
                        text = stringResource(R.string.ask_now),
                    )
                }

            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    BlueSenseTheme {
        Surface {
            BlueBuddyChatBotBanner(navigateToChatBotScreen = {})
        }
    }
}