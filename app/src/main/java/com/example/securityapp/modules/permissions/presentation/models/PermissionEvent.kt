package com.example.securityapp.modules.permissions.presentation.models

sealed interface PermissionEvent {
    data class RequestOtherPermission(val permissions : Array<String>) : PermissionEvent
    data object RequestStoragePermission : PermissionEvent
    data object RequestOverlayPermission : PermissionEvent
    data object RequestMessageDefault : PermissionEvent
    data object NavigateToIntro : PermissionEvent
    data class RequestBackgroundLocationPermission(val permissions : Array<String>) : PermissionEvent
    data class Toast(val str : String) : PermissionEvent
}