package com.byteteam.bluesense.core.helper

import android.util.Log
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import javax.inject.Inject

class MqttHandler @Inject constructor(val memoryPersistence: MemoryPersistence) {
    var client: MqttClient? = null
    fun connect(brokerUrl: String?, clientId: String?, connectOptions: MqttConnectOptions, memoryPersistence: MemoryPersistence, cbOnFailConnect: ((String) -> Unit)? = null) {
        try {
//            // Set up the persistence layer
//            val persistence = MemoryPersistence()
//            persistence.
            // Initialize the MQTT client
            client = MqttClient(brokerUrl, clientId, memoryPersistence)

            // Set up the connection options
            connectOptions.isCleanSession = true

            // Connect to the broker
            client?.connect(connectOptions)
        } catch (e: MqttException) {
            Log.d(this::class.java.simpleName, "connect mqtt error: ${e.message}")
            e.printStackTrace()
            cbOnFailConnect?.invoke(e.message.toString())
        }
    }

    fun disconnect() {
        try {
            client?.disconnect()
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun publish(topic: String?, message: String) {
        try {
            val mqttMessage = MqttMessage(message.toByteArray())
            client?.publish(topic, mqttMessage)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun subscribe(topic: String?) {
        try {
            client?.subscribe(topic)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun unsubscribe(topic: String?) {
        try {
            client?.unsubscribe(topic)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun listen(callbackOnMessage: (topic: String, message: String?) -> Unit, callbackOnTopicNull: (() -> Unit)? = null){
        try {
            client?.setCallback(object: MqttCallback{
                override fun connectionLost(cause: Throwable?) {
                    Log.d(this::class.java.simpleName, "connectionLost: $cause")
                }

                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    Log.d(this::class.java.simpleName, "messageArrived from $topic message: $message")
                    topic?.let {
                        callbackOnMessage(it, message.toString())
                    }
                    if(topic == null) callbackOnTopicNull?.invoke()
                }

                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                    Log.d(this::class.java.simpleName, "deliveryComplete: $token")
                }
            })
        }catch (e: MqttException){
            e.printStackTrace()
        }
    }

    fun close() = client?.close()
}