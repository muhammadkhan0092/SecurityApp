package com.example.securityapp.modules.controlled.presentation.service

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import com.example.securityapp.modules.controlled.presentation.service.OverlayService
import com.example.securityapp.modules.controlled.domain.repository.OverlayRepository

class OverlayControllerImpl(
    private val context: Context
) : OverlayRepository {

    override fun startOverlayService() {
        val intent = Intent(context, OverlayService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context, intent)
        } else {
            context.startService(intent)
        }
    }

    override fun stopOverlayService() {
        val intent = Intent(context, OverlayService::class.java)
        context.stopService(intent)
    }
}