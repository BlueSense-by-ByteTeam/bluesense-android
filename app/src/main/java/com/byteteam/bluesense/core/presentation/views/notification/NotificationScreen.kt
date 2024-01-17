package com.byteteam.bluesense.core.presentation.views.notification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.data.source.local.room.model.NotificationEntity
import com.byteteam.bluesense.core.presentation.views.notification.widgets.DeleteNotificationAlertContent
import com.byteteam.bluesense.core.presentation.views.notification.widgets.NoNotificationItem
import com.byteteam.bluesense.core.presentation.views.notification.widgets.NotificationItem
import com.byteteam.bluesense.core.presentation.widgets.BottomDialog
import com.byteteam.bluesense.core.presentation.widgets.ErrorHandler
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun NotificationScreen(
    notificationState: StateFlow<Resource<List<NotificationEntity>>>,
    getNotification: () -> Unit,
    deleteNotification: () -> Unit,
    closeDeleteModal: () -> Unit,
    isModalOpen: Boolean = false,
    onDelete: Boolean = false,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        getNotification()
    }
    Column {
        if (isModalOpen) {
            BottomDialog(onDismissRequest = closeDeleteModal) {
                DeleteNotificationAlertContent(
                    isOnDelete = onDelete,
                    onDismiss = closeDeleteModal,
                    onConfirm = deleteNotification
                )
            }
        }
            notificationState.collectAsState().value.let {
                when (it) {
                    is Resource.Loading -> CircularProgressIndicator()
                    is Resource.Error -> ErrorHandler(
                        onPressRetry = { getNotification() },
                        errorText = it.message ?: "Error saat mengambil data riwayat notifikasi"
                    )
                    is Resource.Success -> {
                        if (it.data.isNullOrEmpty()) {
                            NoNotificationItem()
                        } else {
                            LazyColumn(modifier) {
                                items(it.data) {item ->
                                    NotificationItem(notificationEntity = item)
                                }
                            }
                        }
                    }
                }
            }

    }
}

@Preview
@Composable
private fun NoficationPreview() {
    BlueSenseTheme {
        Surface {
            NotificationScreen(
                MutableStateFlow(Resource.Loading()),
                {},
                {},
                {},
            )
        }
    }
}