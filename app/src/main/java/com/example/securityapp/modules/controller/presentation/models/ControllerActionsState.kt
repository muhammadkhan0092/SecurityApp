package com.example.securityapp.modules.controller.presentation.models

data class ControllerActionsState(
    val isLoading : Boolean = true,
    val numbers : List<String> = emptyList(),
)
