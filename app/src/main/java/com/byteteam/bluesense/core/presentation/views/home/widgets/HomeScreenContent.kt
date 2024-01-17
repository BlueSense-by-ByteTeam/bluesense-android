package com.byteteam.bluesense.core.presentation.views.home.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.DeviceEntity
import com.byteteam.bluesense.core.domain.model.DeviceLatestInfoEntity
import com.byteteam.bluesense.core.helper.Screens
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreenContent(
    statusDevice: StateFlow<Boolean>,
    deviceEntity: DeviceEntity?,
    deviceInfo: StateFlow<Resource<DeviceLatestInfoEntity?>>,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    waterQualityRealtime: StateFlow<String?>,
    waterStatusRealtime: StateFlow<String?>,
){
    Column(modifier) {
        Text(
            text = stringResource(R.string.home_text),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        DeviceInfoCard(
            statusDevice = statusDevice,
            deviceEntity = deviceEntity,
            waterQualityRealtime = waterQualityRealtime,
            waterStatusRealtime = waterStatusRealtime,
            onTapAddDevice = { navHostController.navigate(Screens.AddDevice.route) },
            onTapDetailDevice = { id -> navHostController.navigate(Screens.DetailDevice.createRoute(id)) },
            deviceData = deviceInfo,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Text(
            text = stringResource(R.string.elevate_your_water_quality),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        WaterFilterBanner()
    }
}