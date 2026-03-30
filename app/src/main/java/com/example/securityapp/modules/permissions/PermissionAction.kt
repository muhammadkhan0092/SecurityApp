package com.example.securityapp.modules.permissions

sealed interface PermissionAction {
    data object OnOverlayAction : PermissionAction
    data object OnStorageAction : PermissionAction
    data object OnOtherAction : PermissionAction
    data object OnBackGroundAction : PermissionAction
    data object OnOtherPermissionGranted : PermissionAction
    data object OnBackgroundPermissionGranted : PermissionAction
    data object OnOverlayPermissionGranted : PermissionAction
    data object OnStoragePermissionGranted : PermissionAction
    data object OnContinueClicked : PermissionAction
    data object OnMessageDefaultClicked : PermissionAction
    data class DefaultAppResult(val bool: Boolean): PermissionAction
}