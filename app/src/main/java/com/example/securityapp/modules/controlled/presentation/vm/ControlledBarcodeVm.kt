package com.example.securityapp.modules.controlled.presentation.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.barcode.generateBarcode
import com.example.securityapp.core.data.repository.DataStoreRepository
import com.example.securityapp.core.data.repository.RoomMessagesRepository
import com.example.securityapp.modules.connection.domain.RemoveConnection
import com.example.securityapp.modules.controlled.data.repository.FirebaseControlledRepository
import com.example.securityapp.modules.controlled.domain.usecase.SyncControlled
import com.example.securityapp.modules.controlled.presentation.models.ControlledAction
import com.example.securityapp.modules.controlled.presentation.models.ControlledEvents
import com.example.securityapp.modules.controlled.presentation.models.ControlledState
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.device_owner.data.AndroidDeviceOwnerRepository
import com.example.securityapp.modules.uninstall.domain.UninstallApps
import com.example.securityapp.modules.controlled.presentation.models.ControlledEvents.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ControlledBarcodeVm @Inject constructor(
    private val datastore: DataStoreRepository,
    private val messagesRepository: RoomMessagesRepository,
    private val firebaseControlledRepository: FirebaseControlledRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val syncControlled: SyncControlled,
    private val removeConnection: RemoveConnection,
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
                    val event = when(result){
                        is Result.Error<*> -> Toast(result.error)
                        is Result.Success -> Toast("Controller Removed Successfully")
                    }
                    _events.emit(event)
                }
            }

            ControlledAction.OnSettingsClicked -> {
                viewModelScope.launch {
                    _events.emit(ControlledEvents.NavigateToSettings)
                }
            }
        }
    }


    init {
        Log.d("KHAN","IN CONTROLLED BARCODE VM")
        viewModelScope.launch {
            datastore.barcode.collectLatest {
                val barcodeBitmap = generateBarcode(useQrCode = true, data = dataStoreRepository.getBarcode())
                _state.update {
                    it.copy(bitmap = barcodeBitmap)
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
                    Log.d("KHAN","DATA FROM FIREBASE IS $it")
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