package com.byteteam.bluesense.core.presentation.views.signup

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.byteteam.bluesense.core.presentation.views.signup.widgets.SignupScreenContent
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@Composable
fun SignupScreen(modifier: Modifier = Modifier) {
    //ui logic should be here
    SignupScreenContent(modifier)
}

@Preview
@Composable
private fun Preview() {
    BlueSenseTheme {
        Surface {
            SignupScreen()
        }
    }
}