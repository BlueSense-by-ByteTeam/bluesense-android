package com.byteteam.bluesense.core.presentation.views.signin

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.core.data.event.SingleEvent
import com.byteteam.bluesense.core.presentation.views.signin.widgets.SigninScreenContent
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun SigninScreen(
    email: String,
    password: String,
    enableButton: Boolean,
    eventMessage: Flow<SingleEvent>,
    onUpdateEmail: (String) -> Unit = {},
    onUpdatePassword: (String) -> Unit = {},
    onTapSignInEmailPassword: () -> Unit = {},
    onTapGoogleAuth: () -> Unit = {},
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    //ui logic should be here
    SigninScreenContent(
        email=email,
        password=password,
        onUpdateEmail=onUpdateEmail,
        onUpdatePassword=onUpdatePassword,
        onTapSignInEmailPassword = onTapSignInEmailPassword,
        navHostController = navHostController,
        enableButton = enableButton,
        eventMessage = eventMessage,
        onTapGoogleAuth = onTapGoogleAuth,  modifier = modifier)
}

@Preview
@Composable
private fun Preview() {
    BlueSenseTheme {
        Surface {
            SigninScreen("", "",true, flowOf())
        }
    }
}