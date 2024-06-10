package com.byteteam.bluesense.core.presentation.views.device.modules.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.domain.model.SensorData
import com.byteteam.bluesense.core.presentation.views.device.modules.detail.widgets.BannerWaterStatus
import com.byteteam.bluesense.core.presentation.views.device.modules.detail.widgets.CardStatusTemplate
import com.byteteam.bluesense.core.presentation.views.device.modules.detail.widgets.DeleteDeviceAlertContent
import com.byteteam.bluesense.core.presentation.views.profile.widgets.SignOutDialogContent
import com.byteteam.bluesense.core.presentation.widgets.BottomDialog
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import com.byteteam.bluesense.ui.theme.Green
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DetailDeviceScreen(
    waterQualityHistory: String?,
    waterQualityRealtime: StateFlow<String?>,
    waterStatusRealtime: StateFlow<String?>,
    statusDevice: StateFlow<Boolean>,
    sensorData: StateFlow<SensorData?>,
    isOnDelete: Boolean = false,
    closeDeleteModal: () -> Unit = {},
    onDeleteDevice: () -> Unit = {},
    isDeleteDialogOpen: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .padding(horizontal = 24.dp)
    ) {
        if(isDeleteDialogOpen){
                BottomDialog(onDismissRequest = closeDeleteModal) {
                    DeleteDeviceAlertContent(
                        isOnDelete = isOnDelete,
                        onDismiss = closeDeleteModal,
                        onConfirm = onDeleteDevice
                    )
                }
        }
        Text(
            "Penampungan Air",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Row(modifier = Modifier.padding(bottom = 20.dp)) {
            Text("Status Alat :")
            Text(
                if (statusDevice.collectAsState().value) "Terhubung" else "Tidak Terhubung",
                color = if (statusDevice.collectAsState().value) Green else Color.Red,
                fontWeight = FontWeight.Bold
            )
        }
        BannerWaterStatus(
            waterQualityHistory = waterQualityHistory,
            waterQualityRealtime = waterQualityRealtime,
            connected = statusDevice.collectAsState().value,
            modifier = Modifier.padding(bottom = 36.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.box_device),
            contentDescription = stringResource(
                id = R.string.device_image
            ),
            modifier = Modifier
                .padding(bottom = 12.dp)
                .align(Alignment.CenterHorizontally)
        )
        CardStatusTemplate(sensorData, waterStatusRealtime, waterQualityRealtime)
    }
}

@Preview
@Composable
private fun Preview() {
    BlueSenseTheme {
        Surface {
            DetailDeviceScreen(
                null,
                MutableStateFlow(null),
                MutableStateFlow(null),
                MutableStateFlow(true),
                MutableStateFlow(null),
            )
        }
    }
}