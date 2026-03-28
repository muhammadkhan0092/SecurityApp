package com.example.securityapp.modules.permissions
data class PermissionState(
    val isOverlayGranted: Boolean,
    val isStorageGranted: Boolean ,
    val isOtherGranted: Boolean,
    val isDefaultAppSet : Boolean
)