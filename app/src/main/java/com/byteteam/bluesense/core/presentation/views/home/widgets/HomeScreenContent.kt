package com.byteteam.bluesense.core.presentation.views.home.widgets

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
import com.byteteam.bluesense.core.domain.model.DeviceEntity
import com.byteteam.bluesense.core.helper.Screens

@Composable
fun HomeScreenContent(
    device: DeviceEntity?,
    navHostController: NavHostController,
){
    Text(
        text = stringResource(R.string.home_text),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 24.dp)
    )
    DeviceInfoCard(
        onTapAddDevice = { navHostController.navigate(Screens.AddDevice.route) },
        onTapDetailDevice = { navHostController.navigate(Screens.DetailDevice.createRoute(it)) },
        deviceData = device,
        modifier = Modifier.padding(bottom = 24.dp)
    )
    Text(
        text = stringResource(R.string.elevate_your_water_quality),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 20.dp)
    )
    WaterFilterBanner()
}