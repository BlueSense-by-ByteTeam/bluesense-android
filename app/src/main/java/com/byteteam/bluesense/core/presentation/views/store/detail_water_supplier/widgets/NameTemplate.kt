package com.byteteam.bluesense.core.presentation.views.store.detail_water_supplier.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.core.presentation.views.store.main.widgets.StarRating

@Composable
fun NameTemplate(
    name: String,
    modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(bottom = 20.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold
            )
        }
    }
}