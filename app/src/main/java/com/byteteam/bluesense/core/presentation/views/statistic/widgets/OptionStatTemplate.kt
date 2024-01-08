package com.byteteam.bluesense.core.presentation.views.statistic.widgets

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.core.presentation.tokens.SortData

@Composable
fun OptionStatTemplate(sortDatas: Map<String, SortData>, selectedData: SortData?, onClick: (SortData?) -> Unit, modifier: Modifier = Modifier){
    Text(text = "Ringkasan Harian", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = modifier.padding(horizontal = 24.dp, vertical = 12.dp))
    LazyRow(contentPadding = PaddingValues(start = 24.dp)) {
        items(sortDatas.keys.toList()) {
            OutlinedButton(
                onClick = { onClick(sortDatas[it]) },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedData==sortDatas[it]) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.padding(end = 12.dp)
            ) {
                Text(text = it, color = if (selectedData==sortDatas[it]) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary)
            }
        }
    }
}