package com.byteteam.bluesense.core.presentation.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import com.byteteam.bluesense.ui.theme.RedDanger

@Composable
fun InputField(
    label: String,
    outlined: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    errorMessage: String? = null,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var isSecure by remember { mutableStateOf(keyboardType == KeyboardType.Password) }
    val icon = if (isSecure) Icons.Filled.Visibility
    else Icons.Outlined.VisibilityOff

    val description = if (isSecure) "Hide password" else "Show password"

    Column {
        if (outlined) {
            OutlinedTextField(
                value = text,
                modifier = modifier,
                visualTransformation = if (!isSecure) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                shape = RoundedCornerShape(12.dp),
                isError = errorMessage != null,
                label = { Text(text = label) },
                onValueChange = {
                    text = it
                },
                trailingIcon = {
                    if (keyboardType == KeyboardType.Password)
                        IconButton(onClick = { isSecure = !isSecure }) {
                            Icon(imageVector = icon, description)
                        }
                }
            )
        } else {
            TextField(
                value = text,
                modifier = modifier,
                visualTransformation = if (!isSecure) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                isError = errorMessage != null,
                onValueChange = {
                    text = it
                },
                label = { Text(text = label) },
                trailingIcon = {
                    if (keyboardType == KeyboardType.Password)
                        IconButton(onClick = { isSecure = !isSecure }) {
                            Icon(imageVector = icon, description)
                        }
                }
            )
        }
        errorMessage?.let {
            Text(text = it, style = MaterialTheme.typography.bodySmall, color = RedDanger)
        }
    }
}

@Preview
@Composable
private fun Preview() {
    BlueSenseTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {

                InputField(label = "regular")
                InputField(label = "outlined", outlined = true)
                InputField(label = "outlined", outlined = true, errorMessage = "Test")
                InputField(label = "outlined", outlined = true, keyboardType = KeyboardType.Password)
            }
        }
    }
}