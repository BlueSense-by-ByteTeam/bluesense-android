package com.byteteam.bluesense.core.presentation.widgets

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.byteteam.bluesense.core.helper.BottomNavItem
import com.byteteam.bluesense.core.helper.bottomNavigationItems

@Composable
fun BottomBar(currentRoute: String, navHostController: NavHostController) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.background) {

        bottomNavigationItems.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if(currentRoute != item.route) navHostController.navigate(item.route)
                },
                icon = { Icon(item.icon, contentDescription = null, tint = if(isSelected) MaterialTheme.colorScheme.primary else Color(0xFFACACAC)) },
            )
        }
    }
}