package com.example.securityapp.modules.intro.presentation.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.securityapp.datastore.AppSettings
import com.example.securityapp.modules.intro.presentation.models.IntroAction
import com.example.securityapp.modules.intro.presentation.models.IntroState
import com.example.securityapp.modules.intro.presentation.models.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class IntroSharedVm @Inject constructor() : ViewModel() {
     var userType : AppSettings.UserType = AppSettings.UserType.controlled
    private val _state = MutableStateFlow(IntroState())
    val state=  _state.asStateFlow()
    fun onAction(action: IntroAction) {
        userType = when(action){
            IntroAction.OnBeControlled -> {
                _state.update {
                    it.copy(userType = UserType.CONTROLLED)
                }
                AppSettings.UserType.controlled
            }
            IntroAction.OnControl -> {
                _state.update {
                    it.copy(userType = UserType.CONTROLLER)
                }
                AppSettings.UserType.controller
            }
            IntroAction.OnContinueClicked -> return
        }
        Log.d("KHAN","NEW USER TYPE IS $userType")
    }

    init {
        Log.d("KHAN","INTRO SHARED VM INIT")
    }
}