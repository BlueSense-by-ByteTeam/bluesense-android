package com.byteteam.bluesense.core.presentation.views.reset_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.data.event.SingleEvent
import com.byteteam.bluesense.core.domain.model.InputData
import com.byteteam.bluesense.core.presentation.views.reset_password.widgets.ResetPasswordContent
import com.byteteam.bluesense.core.presentation.widgets.BottomDialog
import com.byteteam.bluesense.core.presentation.widgets.ErrorAlertContent
import com.byteteam.bluesense.core.presentation.widgets.InputField
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun ResetPasswordScreen(
    sendResetEmail: () -> Unit,
    updateEmail: (String) -> Unit,
    inputEmail: InputData,
    buttonEnabled: Boolean,
    eventMessage: Flow<SingleEvent>,
    onResetState: () -> Unit,
    navHostController:  NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    DisposableEffect(Unit){
        onDispose { onResetState() }
    }
    var errorMessage: String? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        eventMessage?.collect { event ->
            when (event) {
                is SingleEvent.MessageEvent -> errorMessage = event.message
            }
        }
    }
    Column(modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        errorMessage?.let {
            BottomDialog(onDismissRequest = {
                errorMessage = null
            }) {
                ErrorAlertContent(title = "Oops! Terjadi kesalahan saat mengirim email reset password", error = it, onDismiss = {errorMessage = null})
            }
        }
        ResetPasswordContent()
        InputField(label = "Email", onUpdate = updateEmail, value = inputEmail.data, errorMessage = inputEmail.errorMessage, outlined = true, keyboardType = KeyboardType.Email, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = sendResetEmail, enabled = buttonEnabled, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
            Text(text = "Kirim")
        }
    }
}

@Preview
@Composable
private fun Preview() {
    BlueSenseTheme {
        Surface {
//            ResetPasswordScreen()
        }
    }
}