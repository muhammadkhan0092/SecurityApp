package com.example.securityapp.modules.logout.domain

import com.example.securityapp.modules.app_settings.data.AppAppSettingsRepoImpl
import com.example.securityapp.datastore.AppSettings
import com.example.securityapp.modules.controlled.domain.repository.ControlledRepository
import com.example.securityapp.modules.controller.domain.repository.ControllerRepository
import com.example.securityapp.modules.messages.domain.repository.MessagesRepository
import com.example.securityapp.modules.uninstall.domain.UninstallRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Logout @Inject constructor(
    private val appSettingsRepoImpl: AppAppSettingsRepoImpl,
    private val uninstallRepository: UninstallRepository,
    private val messagesRepository: MessagesRepository,
    private val controllerRepository: ControllerRepository,
    private val controlledRepository : ControlledRepository
) {
    suspend operator fun invoke(){
        withContext(Dispatchers.IO) {
            messagesRepository.deleteAllMessages()
            controlledRepository.deleteAll()
            controllerRepository.deleteAll()
            uninstallRepository.deleteAll()
            appSettingsRepoImpl.setEmail("")
            appSettingsRepoImpl.setNumber("")
            appSettingsRepoImpl.setUserType(AppSettings.UserType.not_set)
        }
    }
}