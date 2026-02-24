package com.example.securityapp.core.data

data class DomainControllerDevices(
    val email : String,
    val numbers : List<String>,
    val isBlocked : Boolean = false
)
