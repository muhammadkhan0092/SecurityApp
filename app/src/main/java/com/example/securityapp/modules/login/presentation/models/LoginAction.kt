package com.example.securityapp.modules.login.presentation.models

sealed interface LoginAction {
    data class OnLoginClicked(val email : String,val password : String,val selectedNumber : String) : LoginAction
    data class OnEmailChanged(val email : String) : LoginAction
    data class OnPasswordChanged(val password : String) : LoginAction
    data class OnNumberClick(val number : String) : LoginAction
    data class OnNumberChanged(val number : String) : LoginAction
}