package com.example.securityapp.modules.login.presentation.models

sealed interface LoginEvents {
    data object NavigateToControlledHome : LoginEvents
    data class Toast(val str : String) : LoginEvents
}