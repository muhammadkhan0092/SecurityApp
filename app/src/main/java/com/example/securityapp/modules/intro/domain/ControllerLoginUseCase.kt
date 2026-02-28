package com.example.securityapp.modules.intro.domain

import com.example.securityapp.core.data.repository.ControllerDeviceDto
import com.example.securityapp.core.data.repository.LoginRepository
import com.example.securityapp.modules.controller.domain.usecase.StoreControllerInfoUseCase
import com.example.securityapp.firebase.DtoControllerUser
import com.example.securityapp.modules.controlled.domain.PhoneRepository
import com.example.securityapp.utils.Result
import com.example.securityapp.utils.Result.Error
import javax.inject.Inject

class ControllerLoginUseCase @Inject constructor(
    private val phoneRepository: PhoneRepository,
    private val infoUseCase: StoreControllerInfoUseCase,
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        val isAirplaneOn = phoneRepository.isAirplaneModeOn()
        if (isAirplaneOn) {
            return Error("Disable Airplane Mode")
        }
        val sims = phoneRepository.getSimNumbers()
        if (sims.isEmpty()) {
            return Error("Insert A Sim to Continue")
        }
        val result = loginRepository.insertControllerUser(
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