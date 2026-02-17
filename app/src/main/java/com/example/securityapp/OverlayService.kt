package com.example.securityapp

import android.R
import android.app.*
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.TextView

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
            addView(textView)
        }

        // Layout params for overlay
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        // Start at top-left
        params.gravity = Gravity.TOP or Gravity.START
        params.x = 100
        params.y = 100

        // Make overlay draggable
        overlayView.setOnTouchListener(object : View.OnTouchListener {
            private var initialX = 0
            private var initialY = 0
            private var touchX = 0f
            private var touchY = 0f

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event == null) return false
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = params.x
                        initialY = params.y
                        touchX = event.rawX
                        touchY = event.rawY
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        params.x = initialX + (event.rawX - touchX).toInt()
                        params.y = initialY + (event.rawY - touchY).toInt()
                        windowManager.updateViewLayout(overlayView, params)
                        return true
                    }
                }
                return false
            }
        })

        windowManager.addView(overlayView, params)

        // Start foreground for stability
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
