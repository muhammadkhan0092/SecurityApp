package com.example.securityapp.modules.permissions.domain

import javax.inject.Inject

class AreAllPermissionsGranted @Inject constructor(
    private val permissionRepository: PermissionRepository
) {
    operator fun invoke(): Boolean {
        return permissionRepository.hasOverlayPermission() &&
                permissionRepository.hasManageAllFilesPermission() &&
                permissionRepository.areAllRuntimePermissionsGranted() && permissionRepository.isDefaultMessageAppSet() && permissionRepository.isBackgroundLocationGranted()
    }
}