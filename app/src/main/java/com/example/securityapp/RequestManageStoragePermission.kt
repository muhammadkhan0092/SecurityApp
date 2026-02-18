package com.example.securityapp

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.os.Environment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun RequestManageStoragePermission() {

    val context = LocalContext.current

    var hasPermission by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Environment.isExternalStorageManager()
            } else {
                true
            }
        )
    }

    if (!hasPermission) {
        Button(onClick = {
            val intent = Intent(
                Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                Uri.parse("package:${context.packageName}")
            )
            context.startActivity(intent)
        }) {
            Text("Grant Storage Permission")
        }
    }
}
