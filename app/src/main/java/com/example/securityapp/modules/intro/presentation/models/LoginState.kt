package com.example.securityapp.modules.intro.presentation.models

data class LoginState(
    val email : String = "",
    val password : String = "",
    val numbers : List<String>? = null,
    val selectedNumber : String =""
)