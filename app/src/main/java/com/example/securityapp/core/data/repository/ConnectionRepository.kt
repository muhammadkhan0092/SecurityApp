package com.example.securityapp.core.data.repository

import com.example.securityapp.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ConnectionRepository @Inject constructor(
    private val firestore: FirebaseFirestore,

) {
    val controlledCollection = "controlled"
    val controllerCollection = "controllers"
    suspend fun insertControllerAndControllerData(
        controllerData : ControllerDeviceInController,
        controlledData : ControlledDeviceInControlled
    ): Result<Unit> {
        return try {
            val batch = firestore.batch()
            val controllerReference = firestore.collection(controllerCollection).document(controllerData.email)
            val controlledReference = firestore.collection(controlledCollection).document(controlledData.email)
            batch.set(controlledReference, controlledData)
            batch.set(controllerReference,controllerData)
            batch.commit().await()
            Result.Success(Unit)
        }
        catch (e : Exception){
            Result.Error("")
        }
    }
}