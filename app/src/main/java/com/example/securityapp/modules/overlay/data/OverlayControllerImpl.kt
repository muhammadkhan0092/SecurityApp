package com.example.securityapp.modules.overlay.data

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import com.example.securityapp.modules.overlay.domain.OverlayRepository
import com.example.securityapp.modules.overlay.domain.OverlayService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class OverlayControllerImpl @Inject constructor(
    @ApplicationContext private val context: Context
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