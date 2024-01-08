package com.byteteam.bluesense.core.presentation.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.byteteam.bluesense.R

@Composable
fun NavigationBackButton(navHostController: NavHostController) {
    IconButton(onClick = { navHostController.popBackStack() }) {
        Icon(
            imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(
                id = R.string.back_icon
            )
        )
    }
}