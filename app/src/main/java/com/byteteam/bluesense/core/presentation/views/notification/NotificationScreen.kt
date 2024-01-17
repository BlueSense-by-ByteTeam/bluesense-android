package com.byteteam.bluesense.core.presentation.views.notification

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.data.source.local.room.model.NotificationEntity
import com.byteteam.bluesense.core.presentation.views.notification.widgets.DeleteNotificationAlertContent
import com.byteteam.bluesense.core.presentation.views.notification.widgets.NotificationItem
import com.byteteam.bluesense.core.presentation.widgets.BottomDialog
import com.byteteam.bluesense.core.presentation.widgets.ErrorHandler
import com.byteteam.bluesense.core.presentation.widgets.NavigationBackButton
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
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
            notificationState.collectAsState().value.let {
                when (it) {
                    is Resource.Loading -> CircularProgressIndicator()
                    is Resource.Error -> ErrorHandler(
                        onPressRetry = { getNotification() },
                        errorText = it.message ?: "Error saat mengambil data riwayat notifikasi"
                    )

                    is Resource.Success -> {
                        Log.d("TAG", "NotificationScreen: ${it.data}")
                        Log.d("TAG", "NotificationScreen: ${it.data?.size == 0}")
                        if (it.data?.size == 0 || it.data == null) {
                            Column {
                                Image(
                                    painter = painterResource(id = R.drawable.push_notifications),
                                    modifier = Modifier.size(300.dp),
                                    contentDescription = "no notification data"
                                )
                                Text(
                                    text = "Tidak ada notifikasi",
                                    style = MaterialTheme.typography.titleLarge,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Tidak ada notifikasi yang perlu diperhatikan saat ini.",
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            LazyColumn(modifier) {
                                items(it.data) {
                                    NotificationItem()
                                }
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