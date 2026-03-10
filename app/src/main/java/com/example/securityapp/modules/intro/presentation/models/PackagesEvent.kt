package com.example.securityapp.modules.intro.presentation.models

sealed interface PackagesEvent {
    data object NavigateToControlledMain : PackagesEvent
    data class Toast(val str : String) : PackagesEvent
}