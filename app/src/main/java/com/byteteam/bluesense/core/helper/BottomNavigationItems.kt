package com.byteteam.bluesense.core.helper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(val route: String, val icon: ImageVector, val label: String)

val bottomNavigationItems = listOf(
    BottomNavItem(Screens.Home.route, Icons.Default.Home, "Home"),
    BottomNavItem(Screens.History.route, Icons.Default.History, "History"),
    BottomNavItem(Screens.Store.route, Icons.Default.ShoppingBag, "Store"),
    BottomNavItem(Screens.Store.route, Icons.Default.Chat, "Chat bot"),
    BottomNavItem(Screens.Profile.route, Icons.Default.Person, "Profile"),
)