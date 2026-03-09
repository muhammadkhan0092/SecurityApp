package com.example.securityapp.core.data.ext

import android.util.Log
import com.example.securityapp.core.domain.utils.Result


suspend fun <T> firebaseGetSafeCall(
    action: suspend () -> T?
): Result<T?> {
    return try {
        val result = action()
        Result.Success(result)
    } catch (e: Exception) {
        Log.d("KHAN","ERROR IS ${e.message}")
        Result.Error(e.message?:"Unknown Error")
    }
}

suspend fun firebaseUpsertSafeCall(
    action: suspend () -> Unit
): Result<Unit> {
    return try {
        Result.Success(action())
    } catch (e: Exception) {
        Log.d("KHAN","ERROR IS ${e.message}")
        Result.Error("")
    }
}