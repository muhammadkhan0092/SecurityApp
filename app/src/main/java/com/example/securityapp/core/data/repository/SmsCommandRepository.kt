package com.example.securityapp.core.data.repository

import com.example.securityapp.modules.controller.data.models.MessageFromController
import com.example.securityapp.utils.Result
import com.google.gson.Gson
import com.google.gson.JsonObject
import javax.inject.Inject

class SmsCommandRepository @Inject constructor() {
    val gson = Gson()
    fun serializeToString(data : MessageFromController): Result<String> {
        return try {
            Result.Success(gson.toJson(data))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error("Serialization Error")
        }
    }
    fun deserializeToMessageFromContainer(data : String) : Result<MessageFromController>{
        return try {
            Result.Success(gson.fromJson(data, MessageFromController::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error("Deserialization Error")
        }
    }
}