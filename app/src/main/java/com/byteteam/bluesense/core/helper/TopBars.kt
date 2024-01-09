package com.byteteam.bluesense.core.helper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.byteteam.bluesense.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Topbars(route: String, navHostController: NavHostController) {
    val topAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.background
    )
    when (route) {
        Screens.Home.route -> TopAppBar(
            colors = topAppBarColors,
            title = {
                Text(text = stringResource(id = R.string.bluesense))
            }, actions = {
                IconButton(onClick = { navHostController.navigate(Screens.Notification.route) }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                }
            })

        Screens.History.route -> TopAppBar(colors = topAppBarColors,
            title = {
                Text(text = "Riwayat Kualitas Air")
            })

        Screens.Store.route -> TopAppBar(colors = topAppBarColors,
            title = {
                Text(text = stringResource(id = R.string.bluesense))
            })

        else -> null
    }
}