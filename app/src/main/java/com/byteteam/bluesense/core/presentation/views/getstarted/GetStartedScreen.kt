package com.byteteam.bluesense.core.presentation.views.getstarted

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.byteteam.bluesense.core.presentation.views.getstarted.widgets.GetStartedScreenContent

@Composable
fun GetStartedScreen(
    onFinishOnBoarding: () -> Unit = {},
    navigateSignIn: () -> Unit = {},
    navigateSignUp: () -> Unit = {},
    modifier: Modifier = Modifier
){
    GetStartedScreenContent(onFinishOnBoarding, navigateSignUp, navigateSignIn, modifier=modifier)
}

@Preview
@Composable
private fun GetStartedScreenPreview(){
    GetStartedScreen()
}