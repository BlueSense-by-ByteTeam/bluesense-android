package com.byteteam.bluesense.core.presentation.views.signin

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.core.presentation.views.signin.widgets.SigninScreenContent
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@Composable
fun SigninScreen(
    email: String,
    password: String,
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
        onTapGoogleAuth = onTapGoogleAuth,  modifier = modifier)
}

@Preview
@Composable
private fun Preview() {
    BlueSenseTheme {
        Surface {
            SigninScreen("", "")
        }
    }
}