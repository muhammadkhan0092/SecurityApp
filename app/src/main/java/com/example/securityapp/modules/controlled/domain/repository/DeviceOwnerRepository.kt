package com.example.securityapp.modules.controlled.domain.repository

import com.example.securityapp.core.domain.utils.Result

interface DeviceOwnerRepository{
    fun resetPhone(): Result<Unit>

    fun deleteApp()
    fun uninstallPackage(packageName: String): Result<Unit>
    fun isDeviceOwner() : Boolean
}