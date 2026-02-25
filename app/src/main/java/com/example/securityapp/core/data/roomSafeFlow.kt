package com.example.securityapp.core.data

import com.example.securityapp.utils.Result

suspend fun <T>roomSafeFlow(
    action:suspend ()-> T
):  Result<T>{
    return try {
        Result.Success(action())
    }
    catch (e : Exception){
        Result.Error(e.message?:"")
    }
}