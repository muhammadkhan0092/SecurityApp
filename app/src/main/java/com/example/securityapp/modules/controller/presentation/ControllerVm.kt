package com.example.securityapp.modules.controller.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.modules.controller.data.ControllerRepository
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
    private val syncController : SyncController
) : ViewModel() {

    val state: StateFlow<List<ControllerDomain>?> = controllerRepository.getFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    init {
        viewModelScope.launch {
            controllerRepository.listenData(dataStoreRepositoryImplementation.getEmail()).collectLatest {
                if(it.isNotEmpty()){
                    syncController(it)
                }
            }
        }
    }
}