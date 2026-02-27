package com.example.securityapp.core.data.repository

import android.content.Context
import android.telephony.SmsManager
import com.example.securityapp.modules.controller.data.models.MessageFromControlled
import com.example.securityapp.modules.controller.data.models.MessageFromController
import com.example.securityapp.utils.Result
import com.google.gson.Gson
import javax.inject.Inject

class SmsCommandRepository @Inject constructor(
    private val context: Context
) {
    val gson = Gson()
    fun sendSms(phone: String, message: String) {
        val smsManager = context.getSystemService(SmsManager::class.java)
        smsManager.sendTextMessage(phone, null, message, null, null)
    }
    fun serializeToString(data : MessageFromController): Result<String> {
        return try {
            Result.Success(gson.toJson(data))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error("Serialization Error")
        }
    }
    fun serializeToString(data : MessageFromControlled): Result<String> {
        return try {
            Result.Success(gson.toJson(data))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error("Serialization Error")
        }
    }
    fun deserializeToMessageFromController(data : String) : Result<MessageFromController>{
        return try {
            Result.Success(gson.fromJson(data, MessageFromController::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error("Deserialization Error")
        }
    }
    fun deserializeToMessageFromControlled(data : String) : Result<MessageFromControlled>{
        return try {
            Result.Success(gson.fromJson(data, MessageFromControlled::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error("Deserialization Error")
        }
    }
}