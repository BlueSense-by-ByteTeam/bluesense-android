package com.byteteam.bluesense.core.presentation.views.signup

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.core.presentation.views.signup.widgets.SignupScreenContent
import com.byteteam.bluesense.core.presentation.views.signup.widgets.SignupScreenContentData
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@Composable
fun SignupScreen(signupScreenContentData: SignupScreenContentData, navHostController: NavHostController = rememberNavController(), modifier: Modifier = Modifier) {
    //ui logic should be here
    SignupScreenContent(signupScreenContentData = signupScreenContentData, navHostController = navHostController,  modifier = modifier)
}

@Preview
@Composable
private fun Preview() {
    BlueSenseTheme {
        Surface {
            SignupScreen(SignupScreenContentData())
        }
    }
}