package com.example.securityapp.modules.controller.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.domain.usecase.InsertConnection
import com.example.securityapp.modules.controller.data.ControllerRepository
import com.example.securityapp.modules.controller.data.mapToDevicesDto
import com.example.securityapp.modules.controller.domain.ControllerDomain
import com.example.securityapp.modules.controller.domain.SyncController
import com.example.securityapp.modules.controller.presentation.models.ControllerHomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControllerVm @Inject constructor(
    private val controllerRepository: ControllerRepository,
    private val dataStoreRepositoryImplementation: DataStoreRepositoryImplementation,
    private val syncController : SyncController,
    private val insertConnection: InsertConnection
) : ViewModel() {

    private var controllerDevices : List<ControllerDomain>? = null
    private val _state = MutableStateFlow(ControllerHomeState())
    val state = _state.asStateFlow()
    fun connect(barcode : String)  {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val deviceDto = controllerDevices?.map {
                it.mapToDevicesDto()
            }?:emptyList()
            insertConnection(
                barcode,
                controllerDevicesDto = deviceDto
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
        viewModelScope.launch {
            controllerRepository.getFlow().collectLatest {list->
                _state.update {
                    it.copy(isLoading = false, controlledEmails = list.mapToEmails(), isEmpty = list.isEmpty())
                }
            }
        }
    }
}