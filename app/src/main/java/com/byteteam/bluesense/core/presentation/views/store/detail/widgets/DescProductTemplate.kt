package com.byteteam.bluesense.core.presentation.views.store.detail.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R

@Composable
fun DescProductTemplate(
    desc: String,
    modifier: Modifier = Modifier){
    // TODO: change material part to dynamic 
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(bottom = 20.dp)) {
        Text(text = stringResource(R.string.description), fontWeight = FontWeight.Bold)
        Text(text = desc)
    }
}