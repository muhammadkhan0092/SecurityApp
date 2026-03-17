package com.example.securityapp.core.presentation

import android.app.Activity
import android.app.role.RoleManager
import android.app.role.RoleManager.ROLE_SMS
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d("KHAN","IN MAIN ACTIVITY")
        val setSmsAppIntent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
        setSmsAppIntent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName)
        startActivity(setSmsAppIntent)

        askDefaultSmsHandlerPermission()
        val intent = Intent(this, SmsMonitorService::class.java)
        ContextCompat.startForegroundService(this, intent)
        setContent {
            SecurityAppTheme {
                App()
            }
        }
    }



        var intentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    //showToast("Success requesting ROLE_SMS!")
                } else {
                    //showToast("Failed requesting ROLE_SMS")
                }
            }
    private fun askDefaultSmsHandlerPermission(role: String = ROLE_SMS) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager: RoleManager = getSystemService(RoleManager::class.java)
            // check if the app is having permission to be as default SMS app
            val isRoleAvailable = roleManager.isRoleAvailable(role)
            if (isRoleAvailable) {
                // check whether your app is already holding the default SMS app role.
                val isRoleHeld = roleManager.isRoleHeld(role)
                if (!isRoleHeld) {
                    intentLauncher.launch(roleManager.createRequestRoleIntent(role))
                } else {
                    // Request permission for SMS
                }
            }
        } else {
            val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName)
            startActivityForResult(intent, 1001)
        }

    }
}