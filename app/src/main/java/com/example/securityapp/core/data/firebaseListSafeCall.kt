package com.example.securityapp.core.data

suspend fun <T> firebaseListSafeCall(
    action: suspend () -> List<T>
) : com.example.securityapp.utils.Result<List<T>> {
    return try {
        com.example.securityapp.utils.Result.Success(action())
    }
    catch (e : Exception){
        com.example.securityapp.utils.Result.Error("")
    }
}
suspend fun <T> firebaseGetSafeCall(
    action: suspend () -> T?
) :  com.example.securityapp.utils.Result<T> {
    return try {
        val result = action()
        when(result){
            null -> com.example.securityapp.utils.Result.Error("")
            else ->  com.example.securityapp.utils.Result.Success(result)
        }
    }
    catch (e : Exception){
        com.example.securityapp.utils.Result.Error("")
    }
}

suspend fun  firebaseUpsertSafeCall(
    action: suspend () -> Unit
) :  com.example.securityapp.utils.Result<Unit>{
    return try {
        com.example.securityapp.utils.Result.Success(action())
    }
    catch (e : Exception){
        com.example.securityapp.utils.Result.Error("")
    }
}