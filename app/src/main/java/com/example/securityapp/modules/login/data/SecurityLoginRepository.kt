package com.example.securityapp.modules.login.data
import com.example.securityapp.core.data.ext.firebaseGetSafeCall
import com.example.securityapp.core.data.sources.FirebaseRemoteDataSource
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.firebase.DtoControllerUser
import com.example.securityapp.modules.controlled.data.models.ControlledDeviceDto
import com.example.securityapp.modules.controlled.domain.models.ControlledDomainDevice
import com.example.securityapp.modules.controller.data.models.ControllerDeviceDto
import com.example.securityapp.modules.login.domain.LoginRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SecurityLoginRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dataSource: FirebaseRemoteDataSource

) : LoginRepository {
    val controlledDataCollection = "controlled"
    val controlledUserCollection = "controlled_user"
    override suspend fun insertControlledUser(
        controlledData: ControlledDeviceDto,
        user: ControlledDomainDevice
    ): Result<Unit> {
        return try {
            val batch = firestore.batch()
            val controlledDataRef =
                firestore.collection(controlledDataCollection).document(user.email)
            val controllerUserRef =
                firestore.collection(controlledUserCollection).document(user.email)
            batch.set(controlledDataRef, controlledData)
            batch.set(controllerUserRef, user)
            batch.commit().await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error("")
        }
    }

    val controllerUserCollection = "controller_user"
    val controllerDataCollection = "controllers"
    override suspend fun insertControllerUser(
        controllerData: ControllerDeviceDto,
        user: DtoControllerUser
    ): Result<Unit> {
        return try {
            val batch = firestore.batch()
            val controllerDataReference =
                firestore.collection(controllerDataCollection).document(controllerData.email)
            val controllerUserRef =
                firestore.collection(controllerUserCollection).document(user.email)
            batch.set(controllerUserRef, user)
            batch.set(controllerDataReference, controllerData)
            batch.commit().await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error("")
        }
    }
    override suspend fun getControllerUser(email: String): Result<DtoControllerUser?> {
        return firebaseGetSafeCall<DtoControllerUser>(
            action = {
                dataSource.get(collectionPath = controllerUserCollection, documentId = email)
            }
        )
    }
    override suspend fun getControlledUser(email: String): Result<ControlledDomainDevice?> {
        return firebaseGetSafeCall<ControlledDomainDevice>(
            action = {
                dataSource.get(collectionPath = controlledUserCollection, documentId = email)
            }
        )
    }
}