package com.example.securityapp.modules.intro.presentation.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.modules.intro.domain.ControllerLoginUseCase
import com.example.securityapp.modules.intro.presentation.models.LoginAction
import com.example.securityapp.modules.intro.presentation.models.LoginEvents
import com.example.securityapp.core.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginControllerVm @Inject constructor(
    private val controllerLogin: ControllerLoginUseCase
) : ViewModel() {
    private val _events = MutableSharedFlow<LoginEvents>(replay = 0, extraBufferCapacity = 1)
    val events = _events.asSharedFlow()
    fun onAction(action: LoginAction) {
        when(action){
            is LoginAction.OnEmailChanged -> Unit
            is LoginAction.OnLoginClicked ->{
                viewModelScope.launch {
                    val result = controllerLogin(action.email,action.password)
                    Log.d("KHAN","RESULT OS $result")
                    when(result){
                        is Result.Error<*> -> _events.emit(LoginEvents.Toast(result.error))
                        is Result.Success<*> -> _events.emit(LoginEvents.NavigateToControlledHome)
                    }
                }
            }
            is LoginAction.OnPasswordChanged -> Unit
        }
    }
}