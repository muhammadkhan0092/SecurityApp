package com.example.securityapp.modules.intro.presentation.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.modules.app_settings.data.AppAppSettingsRepoImpl
import com.example.securityapp.datastore.AppSettings
import com.example.securityapp.modules.phone.domain.PhoneRepository
import com.example.securityapp.modules.intro.presentation.models.GateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroVm @Inject constructor(
    private val datastore : AppAppSettingsRepoImpl,
    private val phoneRepository: PhoneRepository
) : ViewModel(){
    private val _state = MutableStateFlow(GateEvents.None)
    val state = _state.asStateFlow()
    init {
        viewModelScope.launch {
            val userType = datastore.getUserType()
            val isPackagesSet = datastore.getIsPackagesSet()
            Log.d("KHAN","USER TYPE IS $userType")
            Log.d("KHAN","PACKAGES SET IS $isPackagesSet")
            when(userType){
                AppSettings.UserType.not_set -> {
                    _state.update {
                        GateEvents.NavigateToPermissions
                    }
                }
                AppSettings.UserType.controller -> {
                    _state.update {
                        GateEvents.NavigateToController
                    }
                }
                AppSettings.UserType.controlled -> {
                    when(isPackagesSet){
                        true ->{
                            _state.update {
                                GateEvents.NavigateToControlled
                            }
                        }
                        false -> {
                            _state.update {
                                GateEvents.NavigateToPackages
                            }
                        }
                    }
                }
                AppSettings.UserType.UNRECOGNIZED -> Unit
            }
        }
    }
}