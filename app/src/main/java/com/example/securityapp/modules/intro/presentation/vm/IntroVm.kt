package com.example.securityapp.modules.intro.presentation.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.modules.controlled.presentation.PhoneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class IntroVm @Inject constructor(
    private val datastore : DataStoreRepositoryImplementation,
    private val phoneRepository: PhoneRepository
) : ViewModel(){
    val userType = datastore.userType
        .map { it }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    init {
        val numbers = phoneRepository.getSimNumbers()
        Log.d("KHAN","PHONE NUMBERS ARE $numbers")
    }
}