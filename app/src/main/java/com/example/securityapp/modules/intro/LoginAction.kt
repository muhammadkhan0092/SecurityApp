package com.example.securityapp.modules.intro

sealed interface LoginAction {
    data class OnLoginClicked(val email : String,val password : String) : LoginAction
    data class OnEmailChanged(val email : String) : LoginAction
    data class OnPasswordChanged(val password : String) : LoginAction
}