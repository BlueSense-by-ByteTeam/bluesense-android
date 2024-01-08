package com.byteteam.bluesense.core.presentation.views.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.core.presentation.views.profile.widgets.ActionMenuItem
import com.byteteam.bluesense.core.presentation.views.profile.widgets.ProfilePic
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@Composable
fun ProfileScreen(
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    Scaffold { padding ->
        Column(
            modifier
                .padding(padding)
                .padding(horizontal = 24.dp)
                .padding(top = 20.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp), modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)) {
                ProfilePic()
                Column {
                    Text(text = "Christina Pauline", fontWeight = FontWeight.Bold)
                    Text(text = "ch***********16@gmail.com")
                }
            }
            Column(Modifier.padding(bottom = 26.dp)) {
                Text(text = "Akun", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom=20.dp))
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    ActionMenuItem(icon = Icons.Default.Person, title = "Profil")
                    ActionMenuItem(icon = Icons.Default.Notifications, title = "Notifikasi")
                }
            }
            Column {
                Text(text = "Umum", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom=20.dp))
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    ActionMenuItem(icon = Icons.Default.Logout, title = "Keluar")
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewProfileScreen(){
    BlueSenseTheme {
        Surface {
            ProfileScreen()
        }
    }
}