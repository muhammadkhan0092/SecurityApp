package com.example.securityapp.modules.intro.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.modules.intro.domain.ControlledLoginUseCase
import com.example.securityapp.modules.intro.presentation.models.LoginAction
import com.example.securityapp.modules.intro.presentation.models.LoginEvents
import com.example.securityapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoginControlledVm @Inject constructor(
    private val login : ControlledLoginUseCase
) : ViewModel() {

    private val _events = MutableSharedFlow<LoginEvents>(replay = 0, extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    fun onAction(action: LoginAction) {
        when(action){
            is LoginAction.OnEmailChanged -> Unit
            is LoginAction.OnLoginClicked -> {
                val id =randomId()
                viewModelScope.launch(Dispatchers.IO){
                    val result = login(action.email,action.password,id)
                    withContext(Dispatchers.Main){
                        when(result){
                            is Result.Error<*> -> _events.emit(LoginEvents.Toast(result.error))
                            is Result.Success<*> -> _events.emit(LoginEvents.NavigateToControlledHome)
                        }
                    }
                }
            }
            is LoginAction.OnPasswordChanged -> Unit
        }
    }
    fun randomId(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }
}