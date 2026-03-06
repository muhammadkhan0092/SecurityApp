package com.example.securityapp.modules.controlled.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.barcode.generateBarcode
import com.example.securityapp.core.data.repository.DataStoreRepository
import com.example.securityapp.core.data.repository.RoomMessagesRepository
import com.example.securityapp.modules.controlled.domain.usecase.RemoveConnection
import com.example.securityapp.modules.controlled.data.repository.FirebaseControlledRepository
import com.example.securityapp.modules.controlled.domain.usecase.SyncControlled
import com.example.securityapp.modules.controlled.presentation.models.ControlledAction
import com.example.securityapp.modules.controlled.presentation.models.ControlledEvents
import com.example.securityapp.modules.controlled.presentation.models.ControlledState
import com.example.securityapp.core.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControlledBarcodeVm @Inject constructor(
    private val datastore: DataStoreRepository,
    private val messagesRepository: RoomMessagesRepository,
    private val firebaseControlledRepository: FirebaseControlledRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val syncControlled: SyncControlled,
    private val removeConnection: RemoveConnection
) : ViewModel() {
    private val _state = MutableStateFlow(ControlledState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<ControlledEvents>(replay = 0, extraBufferCapacity = 1)
    val events = _events.asSharedFlow()
    fun onAction(action: ControlledAction) {
        when (action) {
            is ControlledAction.OnTabSelected -> {
                if (action.index != state.value.selectedIndex) {
                    _state.update {
                        it.copy(selectedIndex = action.index)
                    }
                }
            }
            is ControlledAction.OnDeleteClicked ->{
                viewModelScope.launch(Dispatchers.IO){
                    val result = removeConnection(action.email)
                    when(result){
                        is Result.Error<*> -> ControlledEvents.Toast(result.error)
                        is Result.Success -> ControlledEvents.Toast("Controller Removed Successfully")
                    }
                }
            }
        }
    }


    init {
        viewModelScope.launch {
            datastore.barcode.collectLatest {
                val bitmap = generateBarcode(it)
                _state.update {
                    it.copy(bitmap = bitmap)
                }
            }
        }
        viewModelScope.launch {
            messagesRepository.getAllFlow().collectLatest { list ->
                _state.update {
                    it.copy(messages = list)
                }
            }
        }
        viewModelScope.launch {
            firebaseControlledRepository.listenData(dataStoreRepository.getEmail())
                .collectLatest {
                    it?.let {
                        syncControlled(it)
                    }
                }
        }
        viewModelScope.launch {
            firebaseControlledRepository.getFlow().collectLatest { list->
                _state.update {
                    it.copy(controllers = list)
                }
            }
        }
    }
}