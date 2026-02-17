package com.example.securityapp

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.util.Log

class MyAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                val packageName = event.packageName?.toString()
                Log.d("ACCESS_SERVICE", "Opened app: $packageName")
            }

            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                val text = event.text?.joinToString("")
                Log.d("ACCESS_SERVICE", "Text changed: $text")
            }

            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                Log.d("ACCESS_SERVICE", "View clicked")
            }
        }
    }

    override fun onInterrupt() {
        Log.d("ACCESS_SERVICE", "Service Interrupted")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("ACCESS_SERVICE", "Service Connected")
    }
}
