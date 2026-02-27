package com.example.securityapp.modules.controller.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.core.data.repository.SmsCommandRepository
import com.example.securityapp.modules.controller.data.repository.ControllerMessagesRepository
import com.example.securityapp.modules.controller.domain.ControllerDomain
import com.example.securityapp.modules.controller.presentation.mapToEmails
import com.example.securityapp.modules.controller.presentation.mapToNumbers
import com.example.securityapp.modules.controller.presentation.models.ControllerActionsState
import com.example.securityapp.modules.controller.presentation.models.ControllerTabAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControllerActionsVm @Inject constructor(
    private val messagesRepository: ControllerMessagesRepository,
    private val smsCommandRepository: SmsCommandRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ControllerActionsState())
    val state = _state.asStateFlow()

    fun onNumberReceived(data: ControllerDomain?){
        val numbers = data?.mapToNumbers()
        _state.update {
            it.copy(isLoading = false, numbers = numbers?:emptyList())
        }
    }

    fun onAction(action: ControllerTabAction) {
        when(action){
            ControllerTabAction.OnBlockApps -> {
            }
            ControllerTabAction.OnFactoryReset -> {

            }
            ControllerTabAction.OnLocationFetch -> {}
            is ControllerTabAction.OnTabSelected ->{
                if(action.index!=_state.value.selectedTab){
                    _state.update {
                        it.copy(selectedTab = action.index)
                    }
                }
            }
            ControllerTabAction.OnWipeGallery -> {}
        }
    }

    init {
        viewModelScope.launch {
            messagesRepository.getFlow().collectLatest {list->
                _state.update {
                    it.copy(messages = list)
                }
            }
        }
    }
}