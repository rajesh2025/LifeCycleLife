package com.ramajogi.lifefoodlife.presentation.service

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.ramajogi.lifefoodlife.presentation.notification.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyForegroundService : Service() {

    @Inject
    lateinit var notificationHelper: NotificationHelper

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        startForeground(1, notificationHelper.createNotification("Service Running"))
    }

    override fun onBind(intent: Intent?): IBinder? = null
}