package com.byteteam.bluesense.core.presentation.views.signin.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.domain.model.InputData
import com.byteteam.bluesense.core.presentation.widgets.InputField

@Composable
fun SignupForm(
    onTapGoogleAuth: () -> Unit,
    onTapSignInEmail: () -> Unit,
    email: InputData,
    password: InputData,
    enableButton: Boolean,
    onUpdateEmail: (String) -> Unit,
    onUpdatePassword: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        Column(
            modifier = modifier.padding(bottom = 60.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = onTapGoogleAuth,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.flat_color_icons_google),
                    contentDescription = stringResource(
                        R.string.google_ic
                    )
                )
                Text(
                    text = stringResource(R.string.signin_w_google),
                    modifier = Modifier.padding(start = 10.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(text = stringResource(R.string.or))
            InputField(
                value = email.data,
                errorMessage = email.errorMessage,
                onUpdate = onUpdateEmail,
                label = stringResource(R.string.email),
                outlined = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Email
            )
            InputField(
                value = password.data,
                errorMessage = password.errorMessage,
                onUpdate = onUpdatePassword,
                label = stringResource(R.string.password),
                outlined = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Password
            )
            Text(
                text = stringResource(R.string.forgot_password),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(
                    Alignment.End
                )
            )
        }
        Button(
            enabled = enableButton,
            onClick = { if(enableButton) onTapSignInEmail() }, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp), shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = stringResource(R.string.sign_in),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}