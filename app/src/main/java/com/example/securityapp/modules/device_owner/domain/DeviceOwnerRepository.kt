package com.example.securityapp.modules.device_owner.domain

import com.example.securityapp.core.domain.utils.Result

interface DeviceOwnerRepository{
    fun resetPhone(): Result<Unit>

    fun deleteApp()
    fun uninstallPackage(packageName: String): Result<Unit>
    fun isDeviceOwner() : Boolean
    fun grantPermissions()
}