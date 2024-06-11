package com.byteteam.bluesense.core.presentation.views.chatbot.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun TemplateChat(list: List<String>, onPressed: (String) -> Unit, modifier: Modifier = Modifier) {
    LazyRow(verticalAlignment = Alignment.Bottom, modifier = Modifier.height(128.dp), contentPadding = PaddingValues(start = 24.dp)) {
        items(list) {
            Box(
                modifier = modifier
                    .padding(end = 8.dp)
                    .widthIn(max = 240.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF3E00FF).copy(alpha = 0.1f))
                    .clickable { onPressed(it) }
                    .padding(12.dp)
            ) {
                Text(text = it)
            }
        }
    }
}