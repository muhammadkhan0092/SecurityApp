package com.example.securityapp.modules.controller.presentation.vm

import androidx.lifecycle.ViewModel
import com.example.securityapp.modules.controller.domain.ControllerDomain
import com.example.securityapp.modules.controller.presentation.mapToEmails
import com.example.securityapp.modules.controller.presentation.mapToNumbers
import com.example.securityapp.modules.controller.presentation.models.ControllerActionsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ControllerActionsVm @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(ControllerActionsState())
    val state = _state.asStateFlow()

    fun onNumberReceived(data: ControllerDomain?){
        val numbers = data?.mapToNumbers()
        _state.update {
            it.copy(isLoading = false, numbers = numbers?:emptyList())
        }
    }
}