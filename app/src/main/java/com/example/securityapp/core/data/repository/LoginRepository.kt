package com.example.securityapp.core.data.repository

import com.example.securityapp.domain.DomainDevice
import com.example.securityapp.firebase.DtoControllerUser
import com.example.securityapp.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val firestore: FirebaseFirestore,

) {
    val controlledDataCollection = "controlled"
    val controlledUserCollection = "controlled_user"
    suspend fun insertControlledUser(
        controlledData : ControlledDeviceDto,
        user : DomainDevice
    ): Result<Unit> {
        return try {
            val batch = firestore.batch()
            val controlledDataRef = firestore.collection(controlledDataCollection).document(user.email)
            val controllerUserRef = firestore.collection(controlledUserCollection).document(user.email)
            batch.set(controlledDataRef, controlledData)
            batch.set(controllerUserRef,user)
            batch.commit().await()
            Result.Success(Unit)
        }
        catch (e : Exception){
            Result.Error("")
        }
    }

    val controllerUserCollection = "controller_user"
    val controllerDataCollection = "controllers"
    suspend fun insertControllerUser(
        controllerData : ControllerDeviceDto,
        user : DtoControllerUser
    ): Result<Unit> {
        return try {
            val batch = firestore.batch()
            val controllerDataReference = firestore.collection(controllerDataCollection).document(controllerData.email)
            val controllerUserRef = firestore.collection(controllerUserCollection).document(user.email)
            batch.set(controllerUserRef, user)
            batch.set(controllerDataReference,controllerData)
            batch.commit().await()
            Result.Success(Unit)
        }
        catch (e : Exception){
            Result.Error("")
        }
    }
}