package com.example.securityapp.modules.logout
import com.example.securityapp.core.data.repository.DataStoreRepository
import com.example.securityapp.modules.messages.MessagesRepository
import com.example.securityapp.modules.uninstall.UninstallRepository
import com.example.securityapp.datastore.AppSettings
import com.example.securityapp.modules.controlled.domain.repository.ControlledRepository
import com.example.securityapp.modules.controller.domain.repository.ControllerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
class Logout @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val uninstallRepository: UninstallRepository,
    private val messagesRepository: MessagesRepository,
    private val controllerRepository: ControllerRepository,
    private val controlledRepository : ControlledRepository
) {
    suspend operator fun invoke(){
        withContext(Dispatchers.IO){
            messagesRepository.deleteAllMessages()
            controlledRepository.deleteAll()
            controllerRepository.deleteAll()
            uninstallRepository.deleteAll()
            dataStoreRepository.setEmail("")
            dataStoreRepository.setNumber("")
            dataStoreRepository.setUserType(AppSettings.UserType.not_set)
        }
    }
}