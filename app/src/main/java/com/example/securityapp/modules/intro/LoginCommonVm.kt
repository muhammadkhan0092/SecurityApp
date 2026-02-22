package com.example.securityapp.modules.intro

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class LoginCommonVm @Inject constructor() : ViewModel(){
    fun onAction(action: LoginAction) {
        when(action){
            is LoginAction.OnEmailChanged -> {
                _state.update {
                    it.copy(email = action.email)
                }
            }
            is LoginAction.OnLoginClicked -> Unit
            is LoginAction.OnPasswordChanged ->{
                _state.update {
                    it.copy(password = action.password)
                }
            }
        }
    }

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()


}