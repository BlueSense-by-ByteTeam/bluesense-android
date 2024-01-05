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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.presentation.widgets.InputField

@Composable
fun SignupForm() {
    val context = LocalContext.current
    val termAndPrivacyText = buildAnnotatedString {
        withStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.onBackground
            )
        ) {
            append("Dengan membuat akun maka anda menyetujui")
        }
        pushStringAnnotation(tag = "term", annotation = "term")
        withStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            append(" Kebijakan Penggunaan ")
        }
        pop()
        withStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.onBackground
            )
        ) {
            append("dan")
        }
        pushStringAnnotation(tag = "privacy", annotation = "privacy")
        withStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            append(" Kebijakan Privasi ")
        }
        pop()
        withStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.onBackground
            )
        ) {
            append("kami.")
        }
    }
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
        ClickableText(
            text = termAndPrivacyText, onClick = { offset ->
                termAndPrivacyText.getStringAnnotations(tag = "term", start = offset, end = offset)
                    .firstOrNull()
                    ?.let {
                        // on click operation here
                        Toast.makeText(context, "term", Toast.LENGTH_SHORT).show()
                    }
                termAndPrivacyText.getStringAnnotations(
                    tag = "privacy",
                    start = offset,
                    end = offset
                ).firstOrNull()
                    ?.let {
                        // on click operation here
                        Toast.makeText(context, "privacy", Toast.LENGTH_SHORT).show()
                    }
            },
            style = MaterialTheme.typography.bodyLarge
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