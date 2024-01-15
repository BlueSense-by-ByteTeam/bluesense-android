package com.byteteam.bluesense.core.presentation.views.statistic.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LegendTemplate(){
    Row(Modifier.padding(start = 24.dp, bottom = 32.dp), horizontalArrangement = Arrangement.spacedBy(36.dp)) {
        Legend(indicator = "1", label = "Buruk")
        Legend(indicator = "2", label = "Baik")
    }
}