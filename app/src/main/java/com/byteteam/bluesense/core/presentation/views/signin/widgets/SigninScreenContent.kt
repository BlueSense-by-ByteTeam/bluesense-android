package com.byteteam.bluesense.core.presentation.views.signin.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.helper.Screens

@Composable
fun SigninScreenContent(
    onTapGoogleAuth: () -> Unit,
    onTapSignInEmailPassword: () -> Unit,
    email: String,
    password: String,
    enableButton: Boolean,
    onUpdateEmail: (String) -> Unit,
    onUpdatePassword: (String) -> Unit,
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = if (!isSystemInDarkTheme()) R.drawable.bluesense_logo_with_text else R.drawable.bluesense_logo_w_text_darktheme),
            contentDescription = stringResource(
                R.string.bluesense_logo_with_text
            ),
            modifier = Modifier.padding(bottom = 60.dp)
        )
        SignupForm(
            onTapGoogleAuth = onTapGoogleAuth,
            email = email,
            password = password,
            onUpdateEmail = onUpdateEmail,
            onUpdatePassword = onUpdatePassword,
            enableButton=enableButton,
            onTapSignInEmail = onTapSignInEmailPassword
        )
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = stringResource(R.string.dont_have_account))
            Text(
                modifier = Modifier.clickable { navHostController.navigate(Screens.SignUp.route)  },
                text = stringResource(R.string.create_now),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}