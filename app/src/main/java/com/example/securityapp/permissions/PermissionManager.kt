package com.example.securityapp.permissions

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.net.toUri

class PermissionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun areAllPermissionsGranted(): Boolean {
        return hasOverlayPermission() &&
                hasManageAllFilesPermission() &&
                areAllRuntimePermissionsGranted()
    }
    fun areAllRuntimePermissionsGranted(): Boolean {
        return RuntimePermissions.permissions.all {
            isPermissionGranted(it)
        }
    }

    fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
    fun hasOverlayPermission(): Boolean {
        return Settings.canDrawOverlays(context)
    }
    fun requestOverlayPermission() {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            "package:${context.packageName}".toUri()
        )
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
    fun hasManageAllFilesPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            true
        }
    }
    fun requestManageAllFilesPermission() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            val intent = Intent(
                Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                "package:${context.packageName}".toUri()
            )
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
        else return
    }
}