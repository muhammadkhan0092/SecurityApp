package com.example.securityapp.modules.intro.domain

import android.util.Log
import com.example.securityapp.modules.controller.data.models.ControllerDeviceDto
import com.example.securityapp.modules.intro.data.SecurityLoginRepository
import com.example.securityapp.modules.controller.domain.usecase.StoreControllerInfoUseCase
import com.example.securityapp.firebase.DtoControllerUser
import com.example.securityapp.modules.phone.domain.PhoneRepository
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.core.domain.utils.Result.Error
import javax.inject.Inject

class ControllerLoginUseCase @Inject constructor(
    private val phoneRepository: PhoneRepository,
    private val infoUseCase: StoreControllerInfoUseCase,
    private val securityLoginRepository: SecurityLoginRepository
) {
    suspend operator fun invoke(email: String, password: String,number : String): Result<Unit> {
        val isAirplaneOn = phoneRepository.isAirplaneModeOn()
        if (isAirplaneOn) {
            return Error("Disable Airplane Mode")
        }
        phoneRepository.getSimNumbers()
        val isAlreadyLoggedInResult = securityLoginRepository.getControllerUser(email)
        return when(isAlreadyLoggedInResult){
            is Error<*> -> Error(isAlreadyLoggedInResult.error)
            is Result.Success -> {
                val data = isAlreadyLoggedInResult.data
                when(data){
                    null-> newControllerLogin(email,password,number)
                    else -> alreadyControllerLogin(data,password,data.number)
                }
            }
        }
    }
    private suspend fun alreadyControllerLogin(
        data: DtoControllerUser,
        password: String,
        number: String
    ) : Result<Unit>{
        Log.d("KHAN","CONTROLLER ALREADY LOGIN")
        return when(password==data.password){
            true -> {
                infoUseCase(email = data.email,number = number)
                return Result.Success(Unit)
            }
            false -> Error("Invalid Password")
        }
    }
    suspend fun newControllerLogin(email: String, password: String, number: String): Result<Unit> {
        Log.d("KHAN","CONTROLLER NEW LOGIN")
        val result = securityLoginRepository.insertControllerUser(
            controllerData = ControllerDeviceDto(
                email = email,
                number = number
            ),
            user = DtoControllerUser(
                email = email,
                password = password,
                number = number
            )
        )
        return when (result is Result.Success) {
            true -> infoUseCase(
                email = email,
                number = number
            )
            false -> Error("Error")
        }
    }
}