package com.example.securityapp.modules.controlled.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.barcode.generateBarcode
import com.example.securityapp.core.data.repository.DataStoreRepositoryImplementation
import com.example.securityapp.core.data.repository.RoomMessagesRepository
import com.example.securityapp.modules.controlled.presentation.models.ControlledAction
import com.example.securityapp.modules.controlled.presentation.models.ControlledState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControlledBarcodeVm @Inject constructor(
    private val datastore : DataStoreRepositoryImplementation,
    private val messagesRepository: RoomMessagesRepository
) : ViewModel(){
    private val _state = MutableStateFlow(ControlledState())
    val state = _state.asStateFlow()
    fun onAction(action: ControlledAction) {
        when(action){
            is ControlledAction.OnTabSelected -> {
                if(action.index!=state.value.selectedIndex){
                    _state.update {
                        it.copy(selectedIndex = action.index)
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
            messagesRepository.getAllFlow().collectLatest { list->
                _state.update {
                    it.copy(messages = list)
                }
            }
        }
    }
}