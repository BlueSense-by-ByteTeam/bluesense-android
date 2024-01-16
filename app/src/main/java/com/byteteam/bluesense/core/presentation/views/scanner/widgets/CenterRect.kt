package com.byteteam.bluesense.core.presentation.views.scanner.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R

@Composable
fun CenterRect(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(id = R.drawable.scan_bar), modifier = modifier.size(263.dp), contentDescription = null
    )
}