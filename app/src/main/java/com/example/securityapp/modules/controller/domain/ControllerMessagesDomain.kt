package com.example.securityapp.modules.controller.domain

import com.example.securityapp.modules.controller.data.models.MessageFromControlled

data class ControllerMessagesDomain(
    val message : String,
    val type : MessageFromControlled
)
