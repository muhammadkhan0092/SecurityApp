package com.example.securityapp

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.util.Log
import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.modules.controlled.domain.repository.OverlayRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyAccessibilityService : AccessibilityService() {
    @Inject
    lateinit var dataStoreRepositoryImplementation: DataStoreRepositoryImplementation

    @Inject
    lateinit var overlayRepository: OverlayRepository
    var shouldBlock = false
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                val packageName = event.packageName?.toString()
                Log.d("ACCESS_SERVICE", "Opened app: $packageName")
                if(shouldBlock){
                    overlayRepository.startOverlayService()
                }
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

    init {
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreRepositoryImplementation.shouldBlock.collectLatest {
                shouldBlock = it
            }
        }
    }
}
