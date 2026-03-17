package com.example.securityapp.framework

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.securityapp.sms.SmsReceiverEntryPoint
import com.example.securityapp.sms.SmsService

class SmsMonitorService : Service() {

    private lateinit var observer: SmsObserver
    private lateinit var handlerThread: HandlerThread

    override fun onCreate() {
        super.onCreate()

        startForeground(1, createNotification())

        // Create a dedicated thread for the observer
        handlerThread = HandlerThread("SmsObserverThread")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        observer = SmsObserver(handler, this, SmsService.get(this.applicationContext))

        contentResolver.registerContentObserver(
            Uri.parse("content://sms"),
            true,
            observer
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        contentResolver.unregisterContentObserver(observer)
        handlerThread.quitSafely()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotification(): Notification {
        val channelId = "sms_monitor"
        val manager = getSystemService(NotificationManager::class.java)
        val channel = NotificationChannel(
            channelId,
            "SMS Monitor",
            NotificationManager.IMPORTANCE_LOW
        )
        manager.createNotificationChannel(channel)
        return Notification.Builder(this, channelId)
            .setContentTitle("SMS Monitoring")
            .setContentText("Listening for incoming messages")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
    }
}