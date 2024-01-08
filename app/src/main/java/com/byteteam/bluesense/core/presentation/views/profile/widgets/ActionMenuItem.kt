package com.byteteam.bluesense.core.presentation.views.profile.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.core.helper.bottomBorder

@Composable
fun ActionMenuItem(
    icon: ImageVector,
    title: String,
    onTap: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Row(modifier.fillMaxWidth().height(44.dp).bottomBorder(1.dp, Color(0xFFDADADA))) {
        Icon(imageVector = icon, tint = MaterialTheme.colorScheme.primary, contentDescription = null)
        Text(text = title, modifier = Modifier.padding(start = 10.dp))
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
    }
}