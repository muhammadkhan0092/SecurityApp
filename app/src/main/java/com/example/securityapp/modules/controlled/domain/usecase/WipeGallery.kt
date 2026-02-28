package com.example.securityapp.modules.controlled.domain.usecase

import android.os.Environment
import android.util.Log
import com.example.securityapp.core.data.repository.SmsCommandRepository
import com.example.securityapp.core.domain.InsertMessage
import com.example.securityapp.core.domain.MessageFromControlled
import com.example.securityapp.core.domain.MessageTypeFromControlled
import com.example.securityapp.utils.Result
import java.io.File
import javax.inject.Inject

class WipeGallery @Inject constructor(
    private val smsCommandRepository: SmsCommandRepository,
    private val insertMessage: InsertMessage
) {
    suspend operator fun invoke(
        numbers: List<String>,
        email: String
    ){
        val firstNumber = numbers.firstOrNull()
        deleteAllGalleryFiles()
        val messageFromControlled = MessageFromControlled(
            string = "Factory Reset Complete",
            type = MessageTypeFromControlled.NORMAL
        )
        insertMessage(email,"Gallery Wipe From $email", MessageTypeFromControlled.NORMAL)
        val serializedMessage = smsCommandRepository.serializeToString(messageFromControlled)
        when (serializedMessage) {
            is Result.Error<*> -> {
                firstNumber?.let {
                    smsCommandRepository.sendSms(it, serializedMessage.error)
                }
            }
            is Result.Success -> {
                firstNumber?.let {
                    smsCommandRepository.sendSms(it, serializedMessage.data)
                }
            }
        }
    }
    private fun deleteAllGalleryFiles() {
        Log.d("KHAN","IN DELETING ALL FILES")
        val dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        val pictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

        deleteRecursive(dcim)
        deleteRecursive(pictures)
    }

    private fun deleteRecursive(file: File?) {
        file?.let {
            if (it.isDirectory) {
                Log.d("KHAN","DELETING DIRECTORY ${it.name}")
                it.listFiles()?.forEach { child ->
                    Log.d("KHAN","DELETING ${child.name}")
                    deleteRecursive(child)
                }
            }
            it.delete()
        }
    }
}