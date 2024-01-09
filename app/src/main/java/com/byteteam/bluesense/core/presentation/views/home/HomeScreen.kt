package com.byteteam.bluesense.core.presentation.views.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.BuildConfig
import com.byteteam.bluesense.R
import com.byteteam.bluesense.core.helper.MqttHandler
import com.byteteam.bluesense.core.helper.Screens
import com.byteteam.bluesense.core.presentation.views.home.widgets.DeviceInfoCard
import com.byteteam.bluesense.core.presentation.views.home.widgets.HomeTopBar
import com.byteteam.bluesense.core.presentation.views.home.widgets.WaterFilterBanner
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import org.eclipse.paho.client.mqttv3.MqttConnectOptions

@Composable
fun HomeScreen(
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier) {

    val mqttConnectOptions = MqttConnectOptions()
    mqttConnectOptions.apply {
        this.userName = BuildConfig.MQTT_USERNAME
        this.password = BuildConfig.MQTT_PASSWORD.toCharArray()
    }

    val mqttAndroidClient = MqttHandler()

    mqttAndroidClient.connect(
        BuildConfig.MQTT_HOST,
        "android-client-01",
        mqttConnectOptions
    )
    mqttAndroidClient.subscribe("esp32/dev")

    mqttAndroidClient.listen(callbackOnMessage = { topic, message ->
        Log.d(
            "callback success mqtt",
            "$topic message: $message"
        )
    })

    Scaffold(topBar = { HomeTopBar(navigateNotificationScreen = {
        navHostController.navigate(Screens.Notification.route)
    }) }) {
        Column(
            modifier
                .padding(it)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = stringResource(R.string.home_text),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            DeviceInfoCard(
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
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
private fun Preview() {
    BlueSenseTheme {
        Surface {
            HomeScreen()
        }
    }
}