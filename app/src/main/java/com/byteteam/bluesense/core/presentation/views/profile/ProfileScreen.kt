package com.byteteam.bluesense.core.presentation.views.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.core.domain.model.UserData
import com.byteteam.bluesense.core.helper.Screens
import com.byteteam.bluesense.core.presentation.views.profile.widgets.ActionMenuItem
import com.byteteam.bluesense.core.presentation.views.profile.widgets.ProfilePic
import com.byteteam.bluesense.core.presentation.views.profile.widgets.SignOutDialogContent
import com.byteteam.bluesense.core.presentation.widgets.DialogContainer
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@Composable
fun ProfileScreen(
    userData: UserData? = null,
    onTapSignOut: () -> Unit = {},
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    var triggerDialog by remember { mutableStateOf(false) }


    Box(modifier = Modifier.fillMaxSize()){
        if (triggerDialog) {
            DialogContainer(onDismissRequest = {
                triggerDialog = false
            }) {
                SignOutDialogContent(
                    onDismiss = {
                        triggerDialog = false
                    },
                    onConfirm = {
                        onTapSignOut()
                        triggerDialog = false
                    }
                )
            }
        }

        Column(
            modifier
                .padding(horizontal = 24.dp)
                .padding(top = 20.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                ProfilePic()
                Column {
                    Text(text = userData?.userName ?: "-", fontWeight = FontWeight.Bold)
                    Text(text = userData?.email ?: "-")
                }
            }
            Column(Modifier.padding(bottom = 26.dp)) {
                Text(
                    text = "Akun",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    ActionMenuItem(icon = Icons.Default.Person, title = "Profil")
                    ActionMenuItem(icon = Icons.Default.Notifications, title = "Notifikasi")
                }
            }
            Column {
                Text(
                    text = "Umum",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    ActionMenuItem(
                        icon = Icons.Default.Logout,
                        title = "Keluar",
                        onTap = { triggerDialog = true })
                }
            }
        }
    }

}

@Preview
@Composable
private fun PreviewProfileScreen() {
    BlueSenseTheme {
        Surface {
            ProfileScreen()
        }
    }
}