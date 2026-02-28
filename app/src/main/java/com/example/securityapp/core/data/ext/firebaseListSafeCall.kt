package com.example.securityapp.core.data.ext

import com.example.securityapp.utils.Result

suspend fun <T> firebaseListSafeCall(
    action: suspend () -> List<T>
): Result<List<T>> {
    return try {
        Result.Success(action())
    } catch (e: Exception) {
        Result.Error("")
    }
}

suspend fun <T> firebaseGetSafeCall(
    action: suspend () -> T?
): Result<T?> {
    return try {
        val result = action()
        Result.Success(result)
    } catch (e: Exception) {
        Result.Error(e.message?:"Unknown Error")
    }
}

suspend fun firebaseUpsertSafeCall(
    action: suspend () -> Unit
): Result<Unit> {
    return try {
        Result.Success(action())
    } catch (e: Exception) {
        Result.Error("")
    }
}