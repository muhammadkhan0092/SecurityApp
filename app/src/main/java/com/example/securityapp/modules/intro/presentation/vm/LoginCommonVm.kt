package com.example.securityapp.modules.intro.presentation.vm

import androidx.lifecycle.ViewModel
import com.example.securityapp.modules.intro.presentation.models.LoginState
import com.example.securityapp.modules.intro.presentation.models.LoginAction
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