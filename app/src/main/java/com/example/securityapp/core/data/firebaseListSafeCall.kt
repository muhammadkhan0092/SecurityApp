package com.example.securityapp.core.data

import com.example.securityapp.utils.Result

suspend fun <T> firebaseListSafeCall(
    action: suspend () -> List<T>
): com.example.securityapp.utils.Result<List<T>> {
    return try {
        com.example.securityapp.utils.Result.Success(action())
    } catch (e: Exception) {
        com.example.securityapp.utils.Result.Error("")
    }
}

suspend fun <T> firebaseGetSafeCall(
    action: suspend () -> T?
): Result<T?> {
    return try {
        val result = action()
        Result.Success(result)
    } catch (e: Exception) {
        Result.Error("")
    }
}

suspend fun firebaseUpsertSafeCall(
    action: suspend () -> Unit
): com.example.securityapp.utils.Result<Unit> {
    return try {
        com.example.securityapp.utils.Result.Success(action())
    } catch (e: Exception) {
        com.example.securityapp.utils.Result.Error("")
    }
}