package com.example.securityapp.modules.intro.domain

import android.util.Log
import com.example.securityapp.core.data.models.ControllerDeviceDto
import com.example.securityapp.core.data.repository.SecurityLoginRepository
import com.example.securityapp.modules.controller.domain.usecase.StoreControllerInfoUseCase
import com.example.securityapp.firebase.DtoControllerUser
import com.example.securityapp.modules.controlled.domain.repository.PhoneRepository
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.core.domain.utils.Result.Error
import javax.inject.Inject

class ControllerLoginUseCase @Inject constructor(
    private val phoneRepository: PhoneRepository,
    private val infoUseCase: StoreControllerInfoUseCase,
    private val securityLoginRepository: SecurityLoginRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        val isAirplaneOn = phoneRepository.isAirplaneModeOn()
        if (isAirplaneOn) {
            return Error("Disable Airplane Mode")
        }
        val sims = listOf("12312","123124")
        phoneRepository.getSimNumbers()
        Log.d("KHAN","SIMS ARE $sims")
        if (sims.isEmpty()) {
            return Error("Insert A Sim to Continue")
        }
        val isAlreadyLoggedInResult = securityLoginRepository.getControllerUser(email)
        return when(isAlreadyLoggedInResult){
            is Error<*> -> Error(isAlreadyLoggedInResult.error)
            is Result.Success -> {
                val data = isAlreadyLoggedInResult.data
                when(data){
                    null-> newControllerLogin(email,password,sims)
                    else -> alreadyControllerLogin(data,password)
                }
            }
        }
    }
    private suspend fun alreadyControllerLogin(data: DtoControllerUser,password: String) : Result<Unit>{
        Log.d("KHAN","CONTROLLER ALREADY LOGIN")
        return when(password==data.password){
            true -> {
                infoUseCase(email = data.email)
                return Result.Success(Unit)
            }
            false -> Error("Invalid Password")
        }
    }
    suspend fun newControllerLogin(email : String,password: String,sims : List<String>): Result<Unit> {
        Log.d("KHAN","CONTROLLER NEW LOGIN")
        val result = securityLoginRepository.insertControllerUser(
            controllerData = ControllerDeviceDto(
                email = email,
                numbers = sims
            ),
            user = DtoControllerUser(
                email = email,
                password = password
            )
        )
        return when (result is Result.Success) {
            true -> infoUseCase(
                email = email
            )
            false -> Error("Error")
        }
    }
}