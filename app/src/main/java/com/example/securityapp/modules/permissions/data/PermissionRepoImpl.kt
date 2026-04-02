package com.example.securityapp.modules.permissions.data

import android.app.role.RoleManager
import android.app.role.RoleManager.ROLE_SMS
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.provider.Telephony
import androidx.core.net.toUri
import com.example.securityapp.modules.permissions.domain.PermissionRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PermissionRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PermissionRepository{
    override fun areAllRuntimePermissionsGranted(): Boolean {
        return RuntimePermissions.permissions.all {
            context.isPermissionGranted(it)
        }
    }

    override fun getAllRuntimePermissions(): Array<String> = RuntimePermissions.permissions
    override fun getBackgroundLocationPermission(): Array<String> = RuntimePermissions.backgroundLocation

    override fun hasOverlayPermission(): Boolean {
        return Settings.canDrawOverlays(context)
    }
    override fun requestOverlayPermission() {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            "package:${context.packageName}".toUri()
        )
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
    override fun hasManageAllFilesPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            true
        }
    }
    override fun requestManageAllFilesPermission() {
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
    override fun isDefaultMessageAppSet(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = context.getSystemService(RoleManager::class.java)
            val roleAvailable = roleManager.isRoleAvailable(ROLE_SMS)
            if (roleAvailable) {
                roleManager.isRoleHeld(ROLE_SMS)
            } else {
                false
            }
        } else {
            val defaultSmsPackage = Telephony.Sms.getDefaultSmsPackage(context)
            defaultSmsPackage == context.packageName
        }
    }
    override fun isBackgroundLocationGranted(): Boolean {
        return RuntimePermissions.backgroundLocation.all {
            context.isPermissionGranted(it)
        }
    }
}