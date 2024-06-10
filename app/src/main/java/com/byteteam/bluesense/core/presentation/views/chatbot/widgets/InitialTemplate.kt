package com.byteteam.bluesense.core.presentation.views.chatbot.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R


@Composable
fun InitialTemplate(onTemplatePress: (String) -> Unit, templatePrompts: List<String>, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.Bottom) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.chat_bot),
            contentDescription = null
        )
        Spacer(modifier = Modifier.weight(1f))
        TemplateChat(
            modifier = Modifier.padding(top = 32.dp),
            list = templatePrompts,
            onPressed = onTemplatePress
        )
    }
}