package com.byteteam.bluesense.core.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.byteteam.bluesense.core.presentation.tokens.TextFieldStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownInput(label: String, options: List<Pair<String, Int>>, callbakOnSelect: (Int)  -> Unit, modifier: Modifier = Modifier){
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero)}
    val focusRequester = remember { FocusRequester() }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else {
        Icons.Filled.KeyboardArrowDown
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column {
        TextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            colors = TextFieldStyle.backgroundSurface(),
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textfieldSize = coordinates.size.toSize()
                }
                .focusRequester(focusRequester)
                .onFocusChanged
                {
                    expanded = it.hasFocus
                },
            label = {Text(label)},
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                .background(MaterialTheme.colorScheme.surface)
        ) {
            options.forEach { item ->
                DropdownMenuItem(onClick = {
                    selectedText = item.first
                    expanded = false
                    callbakOnSelect(item.second)
                }, text = {Text(text = item.first)})
            }
        }
    }

}