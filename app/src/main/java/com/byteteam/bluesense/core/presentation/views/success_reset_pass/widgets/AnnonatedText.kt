package com.byteteam.bluesense.core.presentation.views.success_reset_pass.widgets

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle


@Composable
fun AnnonatedText(
    callbackOnTapTryNewEmail: () -> Unit,
    modifier: Modifier = Modifier
) {
    val termAndPrivacyText = buildAnnotatedString {
        withStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.onBackground
            )
        ) {
            append("Belum menerima email? Periksa folder spam  atau ")
        }
        pushStringAnnotation(
            tag = "try",
            annotation = "try"
        )
        withStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            append("coba menggunakan email lain")
        }
        pop()
    }
    ClickableText(
        text = termAndPrivacyText, onClick = { offset ->
            termAndPrivacyText.getStringAnnotations(
                tag = "try",
                start = offset,
                end = offset
            )
                .firstOrNull()
                ?.let { callbackOnTapTryNewEmail() }
        },
        modifier = modifier,
        style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center)
    )
}