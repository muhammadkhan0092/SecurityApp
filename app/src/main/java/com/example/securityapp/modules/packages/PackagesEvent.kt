package com.example.securityapp.modules.packages

sealed interface PackagesEvent {
    data object NavigateToControlledMain : PackagesEvent
    data class Toast(val str : String) : PackagesEvent
}