package com.byteteam.bluesense.core.presentation.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.ui.theme.BlueSenseTheme

@Composable
fun TextButton(
    text: String,
    onClick: () -> Unit,
    buttonType: ButtonType = ButtonType.PRIMARY,
    modifier: Modifier = Modifier
) {
    val textColor = when (buttonType) {
        ButtonType.PRIMARY -> Color.White
        ButtonType.OUTLINED_PRIMARY -> MaterialTheme.colorScheme.primary
        ButtonType.SECONDARY -> MaterialTheme.colorScheme.onSecondary
    }

    val buttonColor = when (buttonType) {
        ButtonType.PRIMARY -> MaterialTheme.colorScheme.primary
        ButtonType.OUTLINED_PRIMARY -> MaterialTheme.colorScheme.background
        ButtonType.SECONDARY -> MaterialTheme.colorScheme.secondary
    }

    OutlinedButton(
        border = BorderStroke(
            width = 2.dp,
            color = if (buttonType == ButtonType.OUTLINED_PRIMARY) MaterialTheme.colorScheme.primary else Color.Transparent
        ),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = buttonColor),
        onClick = onClick, modifier = modifier
    ) {
        Text(text, color = textColor)
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    BlueSenseTheme {
        Surface {
            Column {
                TextButton(text = "Test", onClick = { /*TODO*/ })
                TextButton(
                    text = "Test",
                    buttonType = ButtonType.OUTLINED_PRIMARY,
                    onClick = { /*TODO*/ })

            }
        }
    }
}

enum class ButtonType {
    PRIMARY,
    OUTLINED_PRIMARY,
    SECONDARY
}