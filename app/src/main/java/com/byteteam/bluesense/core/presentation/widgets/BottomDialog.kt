package com.byteteam.bluesense.core.presentation.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.core.helper.CustomDialogPosition
import com.byteteam.bluesense.core.helper.customDialogModifier
import com.byteteam.bluesense.core.helper.verticalScrollDisabled
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    child: @Composable () -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    ModalBottomSheet(
        dragHandle = {},
        sheetState = bottomSheetState,
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier,
        onDismissRequest = {
            onDismissRequest()
        }) {
        Column(Modifier.verticalScrollDisabled()) {
            child()
        }
    }
}