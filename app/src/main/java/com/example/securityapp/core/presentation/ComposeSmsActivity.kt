package com.example.securityapp.core.presentation

import android.app.Activity
import android.app.role.RoleManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.securityapp.app.App
import com.example.securityapp.framework.SmsMonitorService
import com.example.securityapp.ui.theme.SecurityAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeSmsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d("KHAN","IN MAIN ACTIVITY")
        requestDefaultSmsApp(this)
        val intent = Intent(this, SmsMonitorService::class.java)
        ContextCompat.startForegroundService(this, intent)
    }
    fun requestDefaultSmsApp(activity: Activity, requestCode: Int = 1001) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = activity.getSystemService(RoleManager::class.java)
            if (roleManager.isRoleAvailable(RoleManager.ROLE_SMS)) {
                if (!roleManager.isRoleHeld(RoleManager.ROLE_SMS)) {
                    val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS)
                    activity.startActivityForResult(intent, requestCode)
                }
            }
        } else {

            // Pre-Android 10, use setDefaultSmsPackage (deprecated in Q)
        }
    }
}