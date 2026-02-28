package com.example.securityapp.core.data.models

data class DevicesDto(
    val email : String = "",
    val numbers : List<String> = emptyList()
)