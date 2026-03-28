package com.example.securityapp.modules.permissions

import javax.inject.Inject

data class PermissionState(
    val isOverlayGranted: Boolean,
    val isStorageGranted: Boolean ,
    val isOtherGranted: Boolean
)