package com.example.securityapp.modules.controlled.presentation.vm

import androidx.lifecycle.ViewModel
import com.example.securityapp.core.data.repository.DataStoreRepository
import com.example.securityapp.modules.controlled.data.repository.FirebaseControlledRepository
import com.example.securityapp.modules.controlled.domain.usecase.SyncControlled
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ControlledVm @Inject constructor(
    private val firebaseControlledRepository: FirebaseControlledRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val syncControlled: SyncControlled
) : ViewModel() {

}