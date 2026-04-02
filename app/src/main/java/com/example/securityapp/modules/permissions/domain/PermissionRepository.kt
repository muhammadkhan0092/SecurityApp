
package com.example.securityapp.modules.permissions.domain

import com.example.securityapp.modules.permissions.data.RuntimePermissions

interface PermissionRepository{
    fun hasOverlayPermission(): Boolean
    fun requestOverlayPermission()
    fun hasManageAllFilesPermission(): Boolean
    fun requestManageAllFilesPermission()
    fun isDefaultMessageAppSet(): Boolean
    fun isBackgroundLocationGranted(): Boolean
    fun areAllRuntimePermissionsGranted(): Boolean
    fun getAllRuntimePermissions() : Array<String>
    fun getBackgroundLocationPermission() : Array<String>
}