package com.byteteam.bluesense.core.presentation.views.signin.widgets

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.data.event.SingleEvent
import com.byteteam.bluesense.core.helper.Screens
import com.byteteam.bluesense.core.helper.verticalScrollDisabled
import com.byteteam.bluesense.core.presentation.views.profile.widgets.SignOutDialogContent
import com.byteteam.bluesense.core.presentation.views.signin.SignInData
import com.byteteam.bluesense.core.presentation.widgets.BottomDialog
import com.byteteam.bluesense.core.presentation.widgets.DialogContainer
import com.byteteam.bluesense.core.presentation.widgets.ErrorAlertContent
import kotlinx.coroutines.flow.Flow

@Composable
fun SigninScreenContent(
    signInData: SignInData,
    isDarkTheme: Boolean = false,
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    var errorMessage: String? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        signInData.eventMessage?.collect { event ->
            when (event) {
                is SingleEvent.MessageEvent -> errorMessage = event.message
            }
        }
    }

    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        errorMessage?.let {
            BottomDialog(onDismissRequest = {
                errorMessage = null
            }) {
                ErrorAlertContent(
                    title = "Oops! Terjadi kesalahan saat masuk",
                    error = it,
                    onDismiss = { errorMessage = null })
            }
        }
        Image(
            painter = painterResource(id = if (!isDarkTheme) R.drawable.bluesense_logo_with_text else R.drawable.bluesense_logo_w_text_darktheme),
            contentDescription = stringResource(
                R.string.bluesense_logo_with_text
            ),
            modifier = Modifier.padding(bottom = 60.dp)
        )
        SignupForm(
            onTapGoogleAuth = { signInData.onTapGoogleAuth() },
            email = signInData.email,
            password = signInData.password,
            onUpdateEmail = signInData.onUpdateEmail,
            onUpdatePassword = signInData.onUpdatePassword,
            enableButton = signInData.enableButton,
            enableGoogleSigninButton = signInData.enableGooogleSigninButton,
            navigateToResetPasswordScreen = { navHostController.navigate(Screens.ResetPassword.route) },
            onTapSignInEmail = signInData.onTapSignInEmailPassword
        )
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = stringResource(R.string.dont_have_account))
            Text(
                modifier = Modifier.clickable { navHostController.navigate(Screens.SignUp.route) },
                text = stringResource(R.string.create_now),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}