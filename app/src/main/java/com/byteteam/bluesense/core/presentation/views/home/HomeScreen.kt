package com.byteteam.bluesense.core.presentation.views.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.DeviceEntity
import com.byteteam.bluesense.core.domain.model.DeviceLatestInfoEntity
import com.byteteam.bluesense.core.helper.MqttHandler
import com.byteteam.bluesense.core.presentation.views.home.widgets.HomeScreenContent
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    mqttConnectOptions: MqttConnectOptions? = null,
    mqttAndroidClient: MqttHandler? = null,
    memoryPersistence: MemoryPersistence? = null,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    devices: StateFlow<Resource<List<DeviceEntity>>> = MutableStateFlow(Resource.Success(listOf())),
    detailDevice: StateFlow<Resource<DeviceLatestInfoEntity?>> = MutableStateFlow(Resource.Loading()),
    getDevices: () -> Unit = {},
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

    val isRefreshing by remember {
        mutableStateOf(false)
    }
    val state = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        getDevices()
    })


    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        Box() {
            devices.collectAsState().value.let {
                when (it) {
                    is Resource.Loading -> {
                        getDevices()
                        CircularProgressIndicator()
                    }
                    is Resource.Error -> Text(text = "error x${it.message}" ?: "Error")
                    is Resource.Success -> HomeScreenContent(
                        deviceEntity = it.data?.get(0),
                        deviceInfo = detailDevice,
                        navHostController = navHostController,
                        modifier = modifier
//                            .fillMaxSize()
//                            .verticalScroll(rememberScrollState())
                            .pullRefresh(state),
                    )
                }
            }
            PullRefreshIndicator(refreshing = isRefreshing, state = state,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
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