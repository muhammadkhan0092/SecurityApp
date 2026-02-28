package com.example.securityapp.modules.controller.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.core.data.repository.DataStoreRepositoryImplementation
import com.example.securityapp.modules.controller.data.repository.ControllerRepository
import com.example.securityapp.modules.controller.domain.ControllerDomain
import com.example.securityapp.modules.controller.domain.SyncController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
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