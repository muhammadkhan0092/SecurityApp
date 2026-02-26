package com.example.securityapp.modules.controller.presentation

import com.example.securityapp.modules.controller.domain.ControllerDomain
import com.example.securityapp.modules.controller.presentation.models.ControllerHomeState

fun List<ControllerDomain>.mapToEmails(): List<String> {
    return map {
        it.email
    }
}