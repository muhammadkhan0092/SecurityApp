package com.example.securityapp.modules.overlay.domain

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import com.example.securityapp.databinding.OverlayLayoutBinding

class OverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var overlayView: FrameLayout

    override fun onCreate() {
        super.onCreate()
        Log.d("KHAN","ON CREATE SERVICE")
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // Create overlay container
        overlayView = FrameLayout(this).apply {
            setBackgroundColor(0x88000000.toInt()) // semi-transparent black
            val textView = TextView(context).apply {
                text = "Overlay Active"
                setTextColor(0xFFFFFFFF.toInt())
                textSize = 22f
                setPadding(50, 50, 50, 50)
            }
            val overlay = OverlayLayoutBinding.inflate(LayoutInflater.from(context))
            addView(overlay.root)
        }

        // Layout params for overlay
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.TOP or Gravity.START
        params.x = 100
        params.y = 100


        windowManager.addView(overlayView, params)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "overlay_service_channel"
            val channel = NotificationChannel(
                channelId,
                "Overlay Service",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

            val notification: Notification = Notification.Builder(this, channelId)
                .setContentTitle("Overlay Active")
                .setContentText("Security overlay running")
                .setSmallIcon(R.drawable.ic_dialog_info)
                .build()

            startForeground(1, notification)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(overlayView)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}