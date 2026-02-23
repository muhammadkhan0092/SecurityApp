package com.example.securityapp.modules.intro

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.securityapp.datastore.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroSharedVm @Inject constructor() : ViewModel() {
    lateinit var userType : AppSettings.UserType
    fun onAction(action: IntroAction) {
        userType = when(action){
            IntroAction.OnBeControlled -> AppSettings.UserType.controlled
            IntroAction.OnControl -> AppSettings.UserType.controller
        }
        Log.d("KHAN","NEW USER TYPE IS $userType")
    }

    init {
        Log.d("KHAN","INTRO SHARED VM INIT")
    }
}