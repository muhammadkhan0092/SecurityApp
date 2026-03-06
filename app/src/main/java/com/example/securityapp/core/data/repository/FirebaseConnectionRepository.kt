package com.example.securityapp.core.data.repository

import com.example.securityapp.core.data.models.BothDeviceDto
import com.example.securityapp.core.data.models.ControlledDeviceDto
import com.example.securityapp.core.data.models.ControllerDeviceDto
import com.example.securityapp.core.domain.repository.ConnectionRepository
import com.example.securityapp.core.domain.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseConnectionRepository @Inject constructor(
    private val firestore: FirebaseFirestore,

) : ConnectionRepository{
    val controlledCollection = "controlled"
    val controllerCollection = "controllers"
    override suspend fun insertControllerAndControllerData(
        controllerData : ControllerDeviceDto,
        controlledData : ControlledDeviceDto
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
    override suspend fun getControllerAndControlledData(
        controllerEmail: String,
        controlledEmail: String
    ): Result<BothDeviceDto> {

        return try {

            val controllerSnapshot = firestore
                .collection(controllerCollection)
                .document(controllerEmail)
                .get()
                .await()

            val controlledSnapshot = firestore
                .collection(controlledCollection)
                .document(controlledEmail)
                .get()
                .await()

            val controller = controllerSnapshot.toObject(ControllerDeviceDto::class.java)
            val controlled = controlledSnapshot.toObject(ControlledDeviceDto::class.java)

            if (controller != null && controlled != null) {
                Result.Success(BothDeviceDto(controllerDto = controller, controlledDto =controlled))
            } else {
                Result.Error("One or both documents not found")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }
}