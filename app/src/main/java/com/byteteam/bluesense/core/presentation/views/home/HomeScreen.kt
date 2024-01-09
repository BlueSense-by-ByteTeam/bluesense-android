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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

@Composable
fun HomeScreen(
    mqttConnectOptions: MqttConnectOptions? = null,
    mqttAndroidClient: MqttHandler? = null,
    memoryPersistence: MemoryPersistence? = null,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
//    DisposableEffect(lifecycleOwner) {
//        // Create an observer that triggers our remembered callbacks
//        // for sending analytics events
//
//        val mqttCoroutineScope = coroutineScope.launch(Dispatchers.Default) {
//            mqttConnectOptions?.let {options->
//                memoryPersistence?.let { memoryPersistence ->
//
//                    mqttAndroidClient?.connect(
//                        brokerUrl = BuildConfig.MQTT_HOST,
//                        clientId = "android-client-01",
//                        memoryPersistence = memoryPersistence,
//                        connectOptions = options
//                    )
//                }
//            }
//            if (mqttAndroidClient?.client?.isConnected == true) {
//                mqttAndroidClient?.subscribe("esp32/dev")
//                mqttAndroidClient?.listen(callbackOnMessage = { topic, message ->
//                    Log.d(
//                        "callback success mqtt",
//                        "$topic message: $message"
//                    )
//                })
//            }
//        }
//
//        mqttCoroutineScope.start()
//
//        onDispose {
//            mqttAndroidClient?.unsubscribe("esp32/dev")
//            mqttAndroidClient?.disconnect()
//            mqttAndroidClient?.close()
//            mqttCoroutineScope.cancel()
//        }
//    }

    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.home_text),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        DeviceInfoCard(
            onTapAddDevice = { navHostController.navigate(Screens.AddDevice.route) },
            onTapDetailDevice = {},
            deviceData = null,
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

@Preview(device = Devices.PIXEL_4)
@Composable
private fun Preview() {
    BlueSenseTheme {
        Surface {
            HomeScreen()
        }
    }
}