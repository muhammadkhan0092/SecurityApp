package com.example.securityapp.modules.controller.presentation

import com.example.securityapp.modules.controller.domain.models.ControllerDomain

fun List<ControllerDomain>.mapToEmails(): List<String> {
    return map {
        it.email
    }
}
fun ControllerDomain.mapToNumbers(): List<String> {
    return numbers
}