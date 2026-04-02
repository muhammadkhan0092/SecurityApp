package com.example.securityapp.modules.controller.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.modules.app_settings.data.AppAppSettingsRepoImpl
import com.example.securityapp.modules.controller.data.repository.FirebaseControllerRepository
import com.example.securityapp.modules.controller.domain.models.ControllerDomain
import com.example.securityapp.modules.controller.domain.usecase.SyncController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControllerCommonVm @Inject constructor(
    private val firebaseControllerRepository: FirebaseControllerRepository,
    private val appSettingsRepoImpl: AppAppSettingsRepoImpl,
    private val syncController : SyncController
) : ViewModel() {
    val state = firebaseControllerRepository.getFlow().stateIn(
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
            firebaseControllerRepository.listenData(appSettingsRepoImpl.getEmail()).collectLatest {
                it?.let {
                    syncController(it)
                }
            }
        }
    }
}