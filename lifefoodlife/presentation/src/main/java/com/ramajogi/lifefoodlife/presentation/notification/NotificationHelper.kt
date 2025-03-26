package com.ramajogi.lifefoodlife.presentation.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import javax.inject.Inject

class NotificationHelper @Inject constructor(private val context: Context) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotification(content: String): Notification {
        val channelId = "my_channel"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, "Service Notifications", NotificationManager.IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)

        return NotificationCompat.Builder(context, channelId)
            .setContentTitle("My Service")
            .setContentText(content)
            .setSmallIcon(android.R.drawable.stat_notify_chat)
            .build()
    }
}