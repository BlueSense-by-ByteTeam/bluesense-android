package com.byteteam.bluesense.core.presentation.views.signup.widgets

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.presentation.widgets.InputField

@Composable
fun SignupForm() {
    val context = LocalContext.current

    Column {
        Column(
            modifier = Modifier.padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputField(
                label = stringResource(R.string.full_name),
                outlined = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Email
            )
            InputField(
                label = stringResource(R.string.email),
                outlined = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Email
            )
            InputField(
                label = stringResource(R.string.password),
                outlined = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Password
            )
            InputField(
                label = stringResource(R.string.password_confirm),
                outlined = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Password
            )
        }
        TermAndPrivacyText(
            callbackOnTapTerm =
            {
                Toast.makeText(context, "term", Toast.LENGTH_SHORT).show()
            },
            callbackOnTapPrivacy = {
                Toast.makeText(context, "privacy", Toast.LENGTH_SHORT).show()
            },
        )
        Button(
            onClick = { /*TODO*/ }, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp, top = 20.dp), shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = stringResource(R.string.signup_now),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Text(
            text = stringResource(R.string.or), modifier = Modifier
                .padding(bottom = 12.dp)
                .align(Alignment.CenterHorizontally)
        )
        OutlinedButton(
            onClick = { /*TODO*/ },
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
                text = stringResource(R.string.signup_w_google),
                modifier = Modifier.padding(start = 10.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}