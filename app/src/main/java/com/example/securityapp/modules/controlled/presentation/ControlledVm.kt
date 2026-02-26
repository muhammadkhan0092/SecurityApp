package com.example.securityapp.modules.controlled.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.modules.controlled.data.ControlledRepository
import com.example.securityapp.modules.controlled.domain.ControlledDomain
import com.example.securityapp.modules.controlled.domain.SyncControlled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ControlledVm @Inject constructor(
    private val controlledRepository: ControlledRepository,
    private val dataStoreRepositoryImplementation: DataStoreRepositoryImplementation,
    private val syncControlled: SyncControlled
) : ViewModel() {

    val state: StateFlow<List<ControlledDomain>?> = controlledRepository.getFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    init {
        viewModelScope.launch {
            controlledRepository.listenData(dataStoreRepositoryImplementation.getEmail())
                .collectLatest {
                    it?.let {
                        syncControlled(it)
                    }
                }
        }
    }
}