package com.byteteam.bluesense.core.helper

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.presentation.widgets.NavigationBackButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Topbars(actions: Map<String, () -> Unit>, route: String, navHostController: NavHostController) {
    val topAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.background
    )

    when (route) {
        Screens.Home.route -> TopAppBar(
            colors = topAppBarColors,
            title = {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.bluesense)
                )
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
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    text = "Riwayat Kualitas Air"
                )
            })

        Screens.Store.route -> TopAppBar(colors = topAppBarColors,
            title = {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.bluesense)
                )
            })

        Screens.Notification.route -> TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            title = {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    text = stringResource(R.string.notification)
                )
            },
            actions = {
                IconButton(onClick = {
                    actions[Screens.Notification.route]?.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                }
            },
            navigationIcon = { NavigationBackButton(navHostController = navHostController) }
        )

        Screens.AddDevice.route -> TopAppBar(colors = topAppBarColors,
            title = {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.bluesense)
                )
            },
            navigationIcon = { NavigationBackButton(navHostController = navHostController) }
        )

        Screens.AddDeviceForm.route -> TopAppBar(colors = topAppBarColors,
            title = {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    text = "Tambah Alat"
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navHostController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_icon)
                    )
                }
            }
        )

        Screens.DetailDevice.route -> TopAppBar(
            colors = topAppBarColors,
            navigationIcon = {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_icon)
                    )
                }
            },
            title = {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    text = "Detail"
                )
            },
            actions = {
                IconButton(onClick = {
                    actions[Screens.DetailDevice.route]?.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = stringResource(
                            R.string.delete_icon
                        )
                    )
                }
            })

        Screens.WaterSupplierRecommendation.route -> TopAppBar(
            colors = topAppBarColors,
            navigationIcon = {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_icon)
                    )
                }
            },
            title = {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    text = "Supplier Air"
                )
            },
        )

        Screens.FilterRecommendation.route -> TopAppBar(
            colors = topAppBarColors,
            navigationIcon = {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_icon)
                    )
                }
            },
            title = {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    text = "Bluesense"
                )
            },
        )

        Screens.ResetPassword.route -> TopAppBar(
            colors = topAppBarColors,
            navigationIcon = {
                IconButton(onClick = {
                    navHostController.navigate(Screens.SignIn.route) {
                        popUpTo(Screens.ResetPassword.route) {
                            inclusive = true
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_icon)
                    )
                }
            },
            title = {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    text = "Bluesense"
                )
            },
        )

        Screens.SuccessResetPassword.route -> TopAppBar(
            colors = topAppBarColors,
            navigationIcon = {
                IconButton(onClick = {
                    navHostController.navigate(Screens.SignIn.route) {
                        popUpTo(Screens.SuccessResetPassword.route) {
                            inclusive = true
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_icon)
                    )
                }
            },
            title = {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    text = "Bluesense"
                )
            },
        )
        Screens.ChatBot.route -> TopAppBar(
            colors = topAppBarColors,
            navigationIcon = {
                IconButton(onClick = {
                    navHostController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_icon)
                    )
                }
            },
            title = {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    text = "Chatbot"
                )
            },
        )

        else -> null
    }
}