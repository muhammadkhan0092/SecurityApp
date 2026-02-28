package com.example.securityapp.modules.controlled.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.core.data.repository.DataStoreRepositoryImplementation
import com.example.securityapp.modules.controlled.data.repository.ControlledRepository
import com.example.securityapp.modules.controlled.domain.ControlledDomain
import com.example.securityapp.modules.controlled.domain.usecase.SyncControlled
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

}