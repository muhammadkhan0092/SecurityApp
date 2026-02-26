package com.example.securityapp.modules.controller.presentation.models

data class ControllerHomeState(
    val controlledEmails : List<String> = emptyList(),
    val isLoading : Boolean = true,
    val isEmpty : Boolean = false
)
