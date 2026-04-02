package com.example.securityapp.modules.messages.data.repository

import android.content.Context
import android.telephony.SmsManager
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.messages.domain.models.MessageFromControlled
import com.example.securityapp.modules.messages.domain.models.MessageFromController
import com.example.securityapp.modules.messages.domain.repository.MessageSerializer
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidMessageSerializer @Inject constructor(
    @ApplicationContext private val context: Context
) : MessageSerializer {
    val gson = Gson()
    override fun sendSms(phone: String, message: String) {
        val smsManager = context.getSystemService(SmsManager::class.java)
        smsManager.sendTextMessage(phone, null, message, null, null)
    }
    override fun serializeToString(data : MessageFromController): Result<String> {
        return try {
            Result.Success(gson.toJson(data))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error("Serialization Error")
        }
    }
    override fun serializeToString(data : MessageFromControlled): Result<String> {
        return try {
            Result.Success(gson.toJson(data))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error("Serialization Error")
        }
    }
    override fun deserializeToMessageFromController(data : String) : Result<MessageFromController> {
        return try {
            Result.Success(gson.fromJson(data, MessageFromController::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error("Deserialization Error")
        }
    }
    override fun deserializeToMessageFromControlled(data : String) : Result<MessageFromControlled> {
        return try {
            Result.Success(gson.fromJson(data, MessageFromControlled::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error("Deserialization Error")
        }
    }
}