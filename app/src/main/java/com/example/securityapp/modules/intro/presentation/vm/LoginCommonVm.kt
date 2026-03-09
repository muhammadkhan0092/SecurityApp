package com.example.securityapp.modules.intro.presentation.vm

import androidx.lifecycle.ViewModel
import com.example.securityapp.modules.controlled.domain.repository.PhoneRepository
import com.example.securityapp.modules.intro.presentation.models.LoginState
import com.example.securityapp.modules.intro.presentation.models.LoginAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
@HiltViewModel
class LoginCommonVm @Inject constructor(
    private val phoneRepository: PhoneRepository
) : ViewModel(){
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

            is LoginAction.OnNumberClick -> {
                if(state.value.selectedNumber!=action.number){
                    _state.update {
                        it.copy(selectedNumber = action.number)
                    }
                }
            }

            is LoginAction.OnNumberChanged -> {
                _state.update {
                    it.copy(selectedNumber = action.number)
                }
            }
        }
    }

    private val _state = MutableStateFlow(LoginState(
        numbers = phoneRepository.getSimNumbers()
    ))
    val state = _state.asStateFlow()

    init {
        val numbers = phoneRepository.getSimNumbers()
        val selectedNumber = if(numbers.isEmpty()) "" else numbers.first()
        _state.update {
            it.copy(numbers = numbers, selectedNumber = selectedNumber)
        }
    }

}