package com.example.securityapp.modules.controller.presentation.models

sealed interface ControllerTabEvent {
    data class Toast(val str : String) : ControllerTabEvent
}