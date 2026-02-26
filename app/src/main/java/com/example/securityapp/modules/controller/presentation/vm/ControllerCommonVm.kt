package com.example.securityapp.modules.controller.presentation.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.core.data.DomainControllerDevices
import com.example.securityapp.domain.usecase.InsertConnection
import com.example.securityapp.modules.controller.data.ControllerRepository
import com.example.securityapp.modules.controller.data.mapToDevicesDto
import com.example.securityapp.modules.controller.domain.ControllerDomain
import com.example.securityapp.modules.controller.domain.SyncController
import com.example.securityapp.modules.controller.presentation.mapToEmails
import com.example.securityapp.modules.controller.presentation.models.ControllerHomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControllerCommonVm @Inject constructor(
    private val controllerRepository: ControllerRepository,
    private val dataStoreRepositoryImplementation: DataStoreRepositoryImplementation,
    private val syncController : SyncController
) : ViewModel() {
    val state = controllerRepository.getFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )

    var selectedController : ControllerDomain? = null

    fun onItemClicked(email : String){
        selectedController = state.value?.first {
            it.email == email
        }
    }
    init {
        viewModelScope.launch {
            controllerRepository.listenData(dataStoreRepositoryImplementation.getEmail()).collectLatest {
                it?.let {
                    syncController(it)
                }
            }
        }
    }
}