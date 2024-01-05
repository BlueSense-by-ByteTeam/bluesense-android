package com.byteteam.bluesense.core.presentation.views.signup.widgets

import android.widget.Toast
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.byteteam.bluesense.R

@Composable
fun TermAndPrivacyText(
    callbackOnTapTerm: () -> Unit,
    callbackOnTapPrivacy: () -> Unit,
    modifier: Modifier = Modifier
) {
    val termAndPrivacyText = buildAnnotatedString {
        withStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.onBackground
            )
        ) {
            append(stringResource(R.string.term_privacy_opening))
        }
        pushStringAnnotation(
            tag = stringResource(R.string.term),
            annotation = stringResource(id = R.string.term)
        )
        withStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            append(stringResource(R.string.usage_policy))
        }
        pop()
        withStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.onBackground
            )
        ) {
            append(stringResource(R.string.and))
        }
        pushStringAnnotation(
            tag = stringResource(R.string.privacy),
            annotation = stringResource(R.string.privacy)
        )
        withStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            append(stringResource(R.string.privacy_policy))
        }
        pop()
        withStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.onBackground
            )
        ) {
            append(stringResource(R.string.us))
        }
    }
    ClickableText(
        text = termAndPrivacyText, onClick = { offset ->
            termAndPrivacyText.getStringAnnotations(
                tag = "term",
                start = offset,
                end = offset
            )
                .firstOrNull()
                ?.let { callbackOnTapTerm() }
            termAndPrivacyText.getStringAnnotations(
                tag = "privacy",
                start = offset,
                end = offset
            )
                .firstOrNull()
                ?.let { callbackOnTapPrivacy() }
        },
        modifier = modifier,
        style = MaterialTheme.typography.bodyLarge
    )
}