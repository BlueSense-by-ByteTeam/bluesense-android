package com.byteteam.bluesense.core.presentation.views.chatbot.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.ChatEntity
import com.byteteam.bluesense.core.presentation.widgets.ErrorHandler
import kotlinx.coroutines.flow.StateFlow


@Composable
fun NewChatUIHandler(
    newChatUiState: StateFlow<Resource<ChatEntity>>,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    newChatUiState.collectAsState().value.let {
        when (it) {
            is Resource.Error -> ErrorHandler(
                modifier = modifier,
                onPressRetry = onRetry,
                errorText = it.message ?: "Tidak bisa mendapatkan data chat"
            )

            is Resource.Loading -> Column(modifier.fillMaxWidth()) {
                CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
            }

            else -> null
        }
    }
}