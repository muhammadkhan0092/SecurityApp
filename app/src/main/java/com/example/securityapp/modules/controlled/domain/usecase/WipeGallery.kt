package com.example.securityapp.modules.controlled.domain.usecase

import com.example.securityapp.core.domain.InsertMessage
import com.example.securityapp.core.domain.MessageFromControlled
import com.example.securityapp.core.domain.MessageTypeFromControlled
import com.example.securityapp.modules.controlled.data.repository.GalleryRepository
import com.example.securityapp.permissions.PermissionManager
import javax.inject.Inject

class WipeGallery @Inject constructor(
    private val insertMessage: InsertMessage,
    private val permissionManager: PermissionManager,
    private val galleryRepository: GalleryRepository,
    private val sendMessageToController: SendMessageToController
) {
    suspend operator fun invoke(
        numbers: List<String>,
        email: String
    ){
        val firstNumber = numbers.firstOrNull()
        if(!permissionManager.hasManageAllFilesPermission()){
            val messageFromControlled = MessageFromControlled(
                string = "Gallery Wipe Permission Error",
                type = MessageTypeFromControlled.ERROR
            )
            insertMessage(email,"Gallery Wipe From $email Permission Error", MessageTypeFromControlled.NORMAL)
            sendMessageToController(firstNumber,messageFromControlled)
        }
        else{
            galleryRepository.deleteAllGalleryFiles()
            val messageFromControlled = MessageFromControlled(
                string = "Factory Reset Complete",
                type = MessageTypeFromControlled.NORMAL
            )
            insertMessage(email,"Gallery Wipe From $email", MessageTypeFromControlled.NORMAL)
            sendMessageToController(firstNumber,messageFromControlled)
        }
    }
}