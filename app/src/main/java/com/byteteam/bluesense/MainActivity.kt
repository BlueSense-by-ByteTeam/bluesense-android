package com.byteteam.bluesense

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.byteteam.bluesense.core.helper.MqttHandler
import com.byteteam.bluesense.ui.theme.BlueSenseTheme
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        mqttAndroidClient.listen(callbackOnMessage = { topic, message -> Log.d("callback success mqtt", "$topic message: $message") })

        setContent {
            BlueSenseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BlueSenseTheme {
        Greeting("Android")
    }
}