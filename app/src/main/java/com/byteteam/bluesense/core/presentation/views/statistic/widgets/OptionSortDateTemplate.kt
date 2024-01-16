package com.byteteam.bluesense.core.presentation.views.statistic.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.core.presentation.tokens.SortData
import com.byteteam.bluesense.core.presentation.tokens.SortDateLog

@Composable
fun OptionSortDateTemplate(sortDates: Map<String, SortDateLog>, selectedDate: SortDateLog?, onClick: (SortDateLog?) -> Unit, modifier: Modifier = Modifier){
    LazyRow(contentPadding = PaddingValues(start = 24.dp)) {
        items(sortDates.keys.toList()) {
            val borderColor = if (selectedDate==sortDates[it]) MaterialTheme.colorScheme.primary else Color(0xFFD9D9D9)

            OutlinedButton(
                onClick = { onClick(sortDates[it]) },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(width = 1.dp, color = borderColor),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedDate == sortDates[it]) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.padding(end = 12.dp)
            ) {
                Text(
                    text = it,
                    color = if (selectedDate == sortDates[it]) Color.White else Color.Black
                )
            }
        }
    }
}