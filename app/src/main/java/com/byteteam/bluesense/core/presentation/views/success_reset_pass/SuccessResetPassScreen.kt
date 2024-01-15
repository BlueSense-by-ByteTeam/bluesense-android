package com.byteteam.bluesense.core.presentation.views.success_reset_pass

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.helper.Screens
import com.byteteam.bluesense.core.presentation.views.success_reset_pass.widgets.AnnonatedText
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@Composable
fun SuccessResetPassScreen(
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    val intent = Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_APP_EMAIL)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    val context = LocalContext.current

    Column(modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.envelope_rafiki_1),
            contentDescription = null,
            modifier = Modifier.size(202.dp)
        )
        Text(
            text = "Cek email kamu",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = "Bluesense sudah mengirimkan instruksi untuk mengatur ulang kata sandi ke email kamu.",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Button(
            onClick = {
                startActivity(context, intent, null)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Buka aplikasi email")
        }
        OutlinedButton(onClick = {
            navHostController.navigate(Screens.SignIn.route) {
                popUpTo(Screens.SuccessResetPassword.route) {
                    inclusive = true
                }
            }
        }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
            Text(text = "Lewati, saya akan konfirmasi nanti")
        }
        AnnonatedText(
            callbackOnTapTryNewEmail = {
                navHostController.navigate(Screens.ResetPassword.route){
                    popUpTo(Screens.SuccessResetPassword.route){
                        inclusive=true
                    }
                }
            },
        )
    }
}

@Preview
@Composable
private fun PreviewSuccessResetPass() {
    BlueSenseTheme {
        Surface {
            SuccessResetPassScreen()
        }
    }
}