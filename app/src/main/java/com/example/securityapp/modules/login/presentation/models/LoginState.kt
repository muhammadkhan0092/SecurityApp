package com.example.securityapp.modules.login.presentation.models

data class LoginState(
    val email : String = "",
    val password : String = "",
    val numbers : List<String>? = null,
    val selectedNumber : String ="",
    val isLoading : Boolean = false
)