package com.example.securityapp.modules.intro

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.core.data.repository.ControllerUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginControllerVm @Inject constructor(
    private val repository: ControllerUserRepository,
    private val datastore : DataStoreRepositoryImplementation
) : ViewModel() {
    fun onAction(action: LoginAction) {
        when(action){
            is LoginAction.OnEmailChanged -> Unit
            is LoginAction.OnLoginClicked ->{
                viewModelScope.launch {
                    val result = repository.createUser(action.email,action.password)
//                    Log.d("KHAN","RESULT OF CREATING USER IS $result")
//                    Log.d("KHAN","SETUP AFTER ${datastore.getIsSetupCompleted()}")
                    datastore.setIsSetupCompleted(true)
                    datastore.setEmail(action.email)
                    //datastore.setIsSetupCompleted()
                    //Log.d("KHAN","SETUP AFTER ${datastore.getIsSetupCompleted()}")
                }
            }
            is LoginAction.OnPasswordChanged -> Unit
        }
    }
}