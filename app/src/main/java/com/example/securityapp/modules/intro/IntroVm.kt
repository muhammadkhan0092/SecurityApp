package com.example.securityapp.modules.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class IntroVm @Inject constructor(
    private val datastore : DataStoreRepositoryImplementation
) : ViewModel(){
    val userType = datastore.userType
        .map { it }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)
}