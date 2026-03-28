package com.example.securityapp.modules.controlled.presentation.models

sealed interface ControlledEvents {
    object NavigateToSettings : ControlledEvents
    data class Toast(val str : String) : ControlledEvents
}