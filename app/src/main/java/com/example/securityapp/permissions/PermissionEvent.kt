package com.example.securityapp.permissions

sealed interface PermissionEvent {
    data object RequestOtherPermission : PermissionEvent
    data object RequestStoragePermission : PermissionEvent
    data object RequestOverlayPermission : PermissionEvent
    data object NavigateToIntro : PermissionEvent
    data class Toast(val str : String) : PermissionEvent
}