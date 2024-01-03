package com.byteteam.bluesense.core.presentation.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import com.byteteam.bluesense.ui.theme.RedDanger

@Composable
fun InputField(label: String, outlined:  Boolean = false, errorMessage: String? = null,  modifier: Modifier = Modifier){
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Column {
        if (outlined){

            OutlinedTextField(
                value = text,
                shape = RoundedCornerShape(12.dp),
                isError = errorMessage != null,
                label = { Text(text = label) },
                onValueChange = {
                    text = it
                }
            )
        }else{
            TextField(
                value = text,
                isError = errorMessage != null,
                onValueChange = {
                    text = it
                },
                label = { Text(text = label) },
            )
        }
        errorMessage?.let {
            Text(text = it, style = MaterialTheme.typography.bodySmall, color = RedDanger)
        }
    }
}

@Preview
@Composable
private fun Preview(){
    BlueSenseTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {

            InputField(label = "regular")
            InputField(label = "outlined", outlined = true)
            InputField(label = "outlined", outlined = true, errorMessage = "Test")
            }
        }
    }
}