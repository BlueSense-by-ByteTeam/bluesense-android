package com.byteteam.bluesense.core.presentation.views.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.presentation.views.notification.widgets.NotificationItem
import com.byteteam.bluesense.core.presentation.widgets.NavigationBackButton
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                title = {
                    Text(text = stringResource(R.string.notification))
                },
                navigationIcon = { NavigationBackButton(navHostController = navHostController) }
            )
        }
    ) { padding ->
        LazyColumn(modifier.padding(padding)) {
            items(12) {
                NotificationItem()
            }
        }
    }
}

@Preview
@Composable
private fun NoficationPreview() {
    BlueSenseTheme {
        Surface {
            NotificationScreen()
        }
    }
}