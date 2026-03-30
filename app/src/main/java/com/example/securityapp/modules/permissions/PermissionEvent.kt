package com.example.securityapp.modules.permissions

sealed interface PermissionEvent {
    data object RequestOtherPermission : PermissionEvent
    data object RequestStoragePermission : PermissionEvent
    data object RequestOverlayPermission : PermissionEvent
    data object RequestMessageDefault : PermissionEvent
    data object NavigateToIntro : PermissionEvent
    data object RequestBackgroundLocationPermission : PermissionEvent
    data class Toast(val str : String) : PermissionEvent
}