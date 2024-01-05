package com.byteteam.bluesense.core.presentation.views.getstarted

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.byteteam.bluesense.core.presentation.views.getstarted.widgets.GetStartedScreenContent

@Composable
fun GetStartedScreen(
    modifier: Modifier = Modifier
){
    GetStartedScreenContent(modifier)
}

@Preview
@Composable
private fun GetStartedScreenPreview(){
    GetStartedScreen()
}