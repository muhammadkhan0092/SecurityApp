package com.example.securityapp.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.securityapp.permissions.SpecialPermissions.hasManageAllFilesPermission
import com.example.securityapp.permissions.SpecialPermissions.hasOverlayPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PermissionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun areAllPermissionsGranted(): Boolean {
        return hasOverlayPermission(context) &&
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
}