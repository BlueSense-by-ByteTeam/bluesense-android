package com.byteteam.bluesense.core.presentation.views.signin

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.byteteam.bluesense.core.presentation.views.signin.widgets.SigninScreenContent
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@Composable
fun SigninScreen(onTapGoogleAuth: () -> Unit = {}, modifier: Modifier = Modifier) {
    //ui logic should be here
    SigninScreenContent(onTapGoogleAuth = onTapGoogleAuth,  modifier = modifier)
}

@Preview
@Composable
private fun Preview() {
    BlueSenseTheme {
        Surface {
            SigninScreen()
        }
    }
}