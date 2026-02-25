package com.example.securityapp.modules.intro.presentation.models

sealed interface LoginEvents {
    data object NavigateToControlledHome : LoginEvents
    data class Toast(val str : String) : LoginEvents
}