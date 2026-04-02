package com.example.securityapp.modules.permissions.presentation.models

data class PermissionState(
    val isOverlayGranted: Boolean,
    val isStorageGranted: Boolean ,
    val isOtherGranted: Boolean,
    val isDefaultAppSet : Boolean,
    val isBackgroundPermissionGranted : Boolean
)