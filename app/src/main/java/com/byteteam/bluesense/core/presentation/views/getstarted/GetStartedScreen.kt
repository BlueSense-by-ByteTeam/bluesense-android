package com.byteteam.bluesense.core.presentation.views.getstarted

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.core.presentation.views.getstarted.widgets.GetStartedScreenContent

@Composable
fun GetStartedScreen(
    onFinishOnBoarding: () -> Unit = {},
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    GetStartedScreenContent(onFinishOnBoarding, navHostController=navHostController, modifier=modifier)
}

@Preview
@Composable
private fun GetStartedScreenPreview(){
    GetStartedScreen()
}