package com.example.securityapp.modules.controller.presentation.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.core.data.repository.RoomMessagesRepository
import com.example.securityapp.core.domain.models.MessageFromController
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.controller.domain.models.ControllerDomain
import com.example.securityapp.modules.controller.domain.usecase.SendMessageRequestFromController
import com.example.securityapp.modules.controller.presentation.models.ControllerActionsState
import com.example.securityapp.modules.controller.presentation.models.ControllerTabAction
import com.example.securityapp.modules.controller.presentation.models.ControllerTabEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ControllerActionsVm @Inject constructor(
    private val messagesRepository: RoomMessagesRepository,
    private val sendMessageRequestFromController: SendMessageRequestFromController
) : ViewModel() {
    private val _state = MutableStateFlow(ControllerActionsState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<ControllerTabEvent>(replay = 0, extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    fun onNumberReceived(data: ControllerDomain?){
        data?.let {d->
            _state.update {
                it.copy(isLoading = false, number =d.number)
            }
        }
    }

    fun onAction(action: ControllerTabAction) {
        if(action is ControllerTabAction.OnTabSelected){
            if(action.index!=_state.value.selectedTab){
                _state.update {
                    it.copy(selectedTab = action.index)
                }
            }
        }
        else{
            val email = _state.value.email
            viewModelScope.launch(Dispatchers.IO){
                val result = when(action){
                    is ControllerTabAction.OnBlockApps -> sendMessageRequestFromController(action.number, MessageFromController.BLOCK_APPS,email)
                    is ControllerTabAction.OnFactoryReset -> sendMessageRequestFromController(action.number, MessageFromController.FACTORY_RESET,email)
                    is ControllerTabAction.OnLocationFetch -> sendMessageRequestFromController(action.number, MessageFromController.GET_LOCATION,email)
                    is ControllerTabAction.OnTabSelected -> return@launch
                    is ControllerTabAction.OnWipeGallery -> sendMessageRequestFromController(action.number, MessageFromController.WIPE_GALLERY,email)
                    is ControllerTabAction.OnUninstallClicked -> sendMessageRequestFromController(action.number, MessageFromController.UNINSTALL_APPS,email)
                }
                when(result){
                    is Result.Error -> {
                        withContext(Dispatchers.Main) {
                             _events.emit(ControllerTabEvent.Toast(result.error))
                         }
                    }
                    is Result.Success-> Log.d("KHAN","REQUEST SUCCESSFULL")
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            messagesRepository.getFlow(state.value.email).collectLatest { list->
                _state.update {
                    it.copy(messages = list)
                }
            }
        }
    }
}