package com.byteteam.bluesense.core.presentation.views.signin

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
    modifier: Modifier = Modifier,
) {
    //ui logic should be here
    SigninScreenContent(
        email=email,
        password=password,
        onUpdateEmail=onUpdateEmail,
        onUpdatePassword=onUpdatePassword,
        onTapSignInEmailPassword = onTapSignInEmailPassword,
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