package com.example.securityapp.modules.intro

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.core.data.repository.ControllerUserRepository
import com.example.securityapp.core.data.repository.DeviceRepository
import com.example.securityapp.firebase.DtoDevice
import com.example.securityapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Random
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoginControlledVm @Inject constructor(
    private val deviceRepository: DeviceRepository
) : ViewModel() {
    fun onAction(action: LoginAction) {
        when(action){
            is LoginAction.OnEmailChanged -> Unit
            is LoginAction.OnLoginClicked -> {
                viewModelScope.launch {
                    val result = deviceRepository.insertDeviceInfo(DtoDevice(
                        email = action.email,
                        password = action.password,
                        barcodeId = randomId(),
                        phoneNumbers = emptyList(),
                        connectedDevices = emptyList()
                    ))
                    when(result){
                        is Result.Error<*> -> {
                            Log.d("KHAN","ERROR IS ${result.error}")
                        }
                        is Result.Success<*> -> {
                            Log.d("KHAN","SUCCESS")
                        }
                    }
                }
            }
            is LoginAction.OnPasswordChanged -> Unit
        }
    }
    fun randomId(): String {
        return UUID.randomUUID().toString()
    }
}