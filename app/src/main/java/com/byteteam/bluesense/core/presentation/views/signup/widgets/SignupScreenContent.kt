package com.byteteam.bluesense.core.presentation.views.signup.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.helper.Screens

@Composable
fun SignupScreenContent(
    signupScreenContentData: SignupScreenContentData,
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier){
    Column(
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
//            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .fillMaxHeight()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = if (!isSystemInDarkTheme()) R.drawable.bluesense_logo_with_text else R.drawable.bluesense_logo_w_text_darktheme),
            contentDescription = stringResource(
                R.string.bluesense_logo_with_text
            ),
            modifier = Modifier.padding(bottom = 36.dp)
        )
        Text(text = stringResource(R.string.signup_desc), textAlign = TextAlign.Left, modifier = Modifier.padding(bottom = 20.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp),  modifier = Modifier
            .padding(bottom = 24.dp)
            .align(Alignment.Start)) {
            Text(text = stringResource(R.string.already_have_account))
            Text(text = stringResource(R.string.signin_now) , color = MaterialTheme.colorScheme.primary, modifier = Modifier.clickable { navHostController.navigate(Screens.SignIn.route) })
        }
        SignupForm(signupScreenContentData)
    }
}

data class SignupScreenContentData(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val confirmPassword: String? = null,
    val onUpdateName: (String) -> Unit = {},
    val onUpdateEmail: (String) -> Unit = {},
    val onUpdatePassword: (String) -> Unit = {},
    val onUpdateConfirmPassword: (String) -> Unit = {},
    val onTapSignUpEmailPassword: () -> Unit = {},
    val onTapSignUpGoogle: () -> Unit = {},
    val disableButton: Boolean = false
)