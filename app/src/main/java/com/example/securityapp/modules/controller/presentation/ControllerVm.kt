package com.example.securityapp.modules.controller.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.core.data.repository.ControllerDeviceInController
import com.example.securityapp.domain.usecase.InsertConnection
import com.example.securityapp.modules.controlled.presentation.PhoneRepository
import com.example.securityapp.modules.controller.data.ControllerRepository
import com.example.securityapp.modules.controller.data.mapToControlledDeviceForController
import com.example.securityapp.modules.controller.domain.ControllerDomain
import com.example.securityapp.modules.controller.domain.SyncController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControllerVm @Inject constructor(
    private val controllerRepository: ControllerRepository,
    private val dataStoreRepositoryImplementation: DataStoreRepositoryImplementation,
    private val syncController : SyncController,
    private val insertConnection: InsertConnection,
    private val phoneRepository : PhoneRepository
) : ViewModel() {

    val state: StateFlow<List<ControllerDomain>?> = controllerRepository.getFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    fun connect(barcode : String)  {
        viewModelScope.launch {
            insertConnection(
                barcode,
                controllerData = ControllerDeviceInController(
                    email = dataStoreRepositoryImplementation.getEmail(),
                    numbers = phoneRepository.getSimNumbers(),
                    devices = state.value?.map {
                        it.mapToControlledDeviceForController(barcode)
                    }?:emptyList()
                )
            )
        }
    }

    init {
        viewModelScope.launch {
            controllerRepository.listenData(dataStoreRepositoryImplementation.getEmail()).collectLatest {
                Log.d("KHAN","NEW DATA RECEIVED IN CONTROLLER IS $it")
                it?.let {
                    syncController(it)
                }
            }
        }
    }
}