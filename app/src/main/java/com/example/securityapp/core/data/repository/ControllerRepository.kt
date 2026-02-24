package com.example.securityapp.core.data.repository

import com.example.securityapp.core.data.firebaseGetSafeCall
import com.example.securityapp.core.data.firebaseUpsertSafeCall
import com.example.securityapp.core.data.sources.FirebaseRemoteDataSource
import com.example.securityapp.utils.Result
import javax.inject.Inject

class ControllerRepository @Inject constructor(
    private val source : FirebaseRemoteDataSource
) {
    val collectionId = "controllers"
    suspend fun upsertData(data : ControllerDeviceInController): Result<Unit> {
        return firebaseUpsertSafeCall(
            action = {
                source.addData(documentId =data.email, collectionId = collectionId,data)
            }
        )
    }
    suspend fun getData(email : String) : Result<ControllerDeviceInController?> {
        return  firebaseGetSafeCall<ControllerDeviceInController>(
            action = {
                source.get(collectionPath = collectionId, documentId = email)
            }
        )
    }
}