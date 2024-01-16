package com.byteteam.bluesense.core.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.byteteam.bluesense.MainActivity
import com.byteteam.bluesense.R
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {
    private val TAG = this.javaClass.simpleName
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: $token")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "From: ${message.from}")
        Log.d(TAG, "Message data payload: " + message.data)
        Log.d(TAG, "Message Notification Body: ${message.notification?.body}")

        sendNotification(message.notification?.title, message.notification?.body)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification(title: String?, messageBody: String?) {
        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(applicationContext,
            NOTIFICATION_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.bluesense_ic_white_no_bg)
            .setColor(android.graphics.Color.BLUE)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID)
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.setDefaults(Notification.DEFAULT_ALL).setPriority(
            NotificationCompat.PRIORITY_MAX)
            .build())
    }


    companion object {
        private val TAG = FCMService::class.java.simpleName
        private const val NOTIFICATION_ID = 1
        private const val NOTIFICATION_CHANNEL_ID = "Firebase Cloud Messaging Channel"
        private const val NOTIFICATION_CHANNEL_NAME = "Firebase Notification"


        fun subscribeTopic(context: Context, topic: String){
            try {

                FirebaseMessaging.getInstance().subscribeToTopic(topic)
                Toast.makeText(context, "Notifikasi aktif untuk device id: $topic", Toast.LENGTH_SHORT).show()
            }catch (e: Exception){
                Toast.makeText(context, "Gagal menginisiasi notifikasi. Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}