import android.content.Context
import android.util.Log
import info.mqtt.android.service.MqttAndroidClient
import info.mqtt.android.service.QoS
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.util.UUID

class MqttClientHelper(
    private val context: Context,
    private val broker: String
){
    var client: MqttAndroidClient? = null
    init {
        client = MqttAndroidClient(context, broker,  UUID.randomUUID().toString())
    }
    fun init(cbOnMessage: (String) -> Unit, cbOnConnected: () -> Unit, cbOnLostConnection: () -> Unit){
        client?.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(reconnect: Boolean, serverURI: String) {
                if (reconnect) {
                    Log.d("MQTT Topic   ", "connectComplete: reconnect")
                } else {
                    Log.d("MQTT Topic   ", "connectComplete: connected")
                }
                cbOnConnected()
            }

            override fun connectionLost(cause: Throwable?) {
                Log.d("MQTT Topic   ", "connection lost")
                cbOnLostConnection()
            }

            override fun messageArrived(topic: String, message: MqttMessage) {
                Log.d("MQTT Topic   ", "connection message ${message.payload}")
                cbOnMessage(message.toString())
            }

            override fun deliveryComplete(token: IMqttDeliveryToken) {}
        })
    }

    fun connect(mqttConnectOptions: MqttConnectOptions, topic: String){
        client?.connect(mqttConnectOptions, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                val disconnectedBufferOptions = DisconnectedBufferOptions()
                disconnectedBufferOptions.isBufferEnabled = true
                disconnectedBufferOptions.bufferSize = 100
                disconnectedBufferOptions.isPersistBuffer = false
                disconnectedBufferOptions.isDeleteOldestMessages = false
                client?.setBufferOpts(disconnectedBufferOptions)
                client?.subscribeToTopic(topic)
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d("MQTT Topic   ", "onFailure: fail to connect")
            }
        })
    }

    private fun MqttAndroidClient.subscribeToTopic(topic: String, cbOnConnected: () -> Unit = {}, cbOnFail: () -> Unit = {}) {
        this.subscribe(topic, QoS.AtLeastOnce.value, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                Log.d("MQTT Topic   ", "Subscribed! $topic")
                cbOnConnected()
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d("MQTT Topic   ", "Failed to subscribe $exception")
                cbOnFail()
            }
        })
    }
}