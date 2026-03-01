package com.example.securityapp.modules.controller.presentation.models

import com.example.securityapp.modules.controller.domain.models.MessagesDomain

data class ControllerActionsState(
    val isLoading : Boolean = true,
    val numbers : List<String> = emptyList(),
    val messages : List<MessagesDomain> = emptyList(),
    val selectedTab : Int = 0,
    val email : String=""
)
