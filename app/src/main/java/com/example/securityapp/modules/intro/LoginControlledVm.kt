package com.example.securityapp.modules.intro

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.core.data.repository.ControllerUserRepository
import com.example.securityapp.core.data.repository.DeviceRepository
import com.example.securityapp.datastore.AppSettings
import com.example.securityapp.firebase.DtoDevice
import com.example.securityapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Random
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoginControlledVm @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val dataStoreRepositoryImplementation: DataStoreRepositoryImplementation
) : ViewModel() {

    private val _events = MutableSharedFlow<LoginEvents>(replay = 0, extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    fun onAction(action: LoginAction) {
        when(action){
            is LoginAction.OnEmailChanged -> Unit
            is LoginAction.OnLoginClicked -> {
                val id =randomId()
                viewModelScope.launch {
                    val result = deviceRepository.insertDeviceInfo(DtoDevice(
                        email = action.email,
                        password = action.password,
                        barcodeId = id,
                        phoneNumbers = emptyList(),
                        connectedDevices = emptyList()
                    ))
                    when(result){
                        is Result.Error<*> -> {
                            Log.d("KHAN","ERROR IS ${result.error}")
                        }
                        is Result.Success<*> -> {
                            val emailResult = dataStoreRepositoryImplementation.setEmail(action.email)
                            val isSetupResult = dataStoreRepositoryImplementation.setIsSetupCompleted(true)
                            val userTypeResult = dataStoreRepositoryImplementation.setUserType(AppSettings.UserType.controlled)
                            val barcodeResult = dataStoreRepositoryImplementation.setBarcode(id)
                            Log.d("KHAN","SUCCESS")
                            when(emailResult && isSetupResult && userTypeResult && barcodeResult){
                                true -> {
                                    _events.emit(LoginEvents.NavigateToControlledHome)
                                }
                                false -> _events.emit(LoginEvents.Toast("Error in Datastore"))
                            }
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