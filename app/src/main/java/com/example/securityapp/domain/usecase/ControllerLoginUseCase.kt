package com.example.securityapp.domain.usecase

import com.example.securityapp.core.data.repository.ControllerDeviceInController
import com.example.securityapp.core.data.repository.LoginRepository
import com.example.securityapp.domain.StoreControllerInfoUseCase
import com.example.securityapp.firebase.DtoControllerUser
import com.example.securityapp.modules.controlled.PhoneRepository
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
            controllerData = ControllerDeviceInController(
                email = email,
                numbers = sims,
                devices = emptyList()
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