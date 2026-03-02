package com.example.securityapp.modules.controlled.presentation.models

sealed interface ControlledEvents {
    data class Toast(val str : String) : ControlledEvents
}