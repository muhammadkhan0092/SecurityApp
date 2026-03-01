package com.example.securityapp.modules.controller.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.domain.usecase.InsertConnection
import com.example.securityapp.modules.controller.data.mappers.mapToDevicesDto
import com.example.securityapp.modules.controller.domain.models.ControllerDomain
import com.example.securityapp.modules.controller.presentation.mapToEmails
import com.example.securityapp.modules.controller.presentation.models.ControllerHomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControllerHomeVm @Inject constructor(
    private val insertConnection: InsertConnection
) : ViewModel() {

    private val _state = MutableStateFlow(ControllerHomeState())
    val state = _state.asStateFlow()
    fun connect(barcode : String,list : List<ControllerDomain>?)  {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val deviceDto = list?.map {
                it.mapToDevicesDto()
            }?:emptyList()
            insertConnection(
                barcode,
                controllerDevicesDto = deviceDto
            )
        }
    }

    fun onStateChanged(state : List<ControllerDomain>){
        _state.update {
            it.copy(isLoading = false, controlledEmails = state.mapToEmails(), isEmpty = state.isEmpty())
        }
    }

}