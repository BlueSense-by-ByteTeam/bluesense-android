package com.byteteam.bluesense.core.presentation.views.signin

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.core.data.event.SingleEvent
import com.byteteam.bluesense.core.domain.model.InputData
import com.byteteam.bluesense.core.presentation.views.signin.widgets.SigninScreenContent
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun SigninScreen(
    signInData: SignInData = SignInData(),
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    //ui logic should be here
    SigninScreenContent(
            signInData=signInData,

         modifier = modifier)
}

data class SignInData(
    val email: InputData = InputData(""),
    val password: InputData = InputData(""),
    val enableButton: Boolean = true,
    val enableGooogleSigninButton: Boolean = true,
    val eventMessage: Flow<SingleEvent> = flowOf(),
    val onUpdateEmail: (String) -> Unit = {},
    val onUpdatePassword: (String) -> Unit = {},
    val onTapSignInEmailPassword: () -> Unit = {},
    val onTapGoogleAuth: () -> Unit = {},
)

@Preview
@Composable
private fun Preview() {
    BlueSenseTheme {
        Surface {
            SigninScreen(SignInData())
        }
    }
}