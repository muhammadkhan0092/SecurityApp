package com.example.securityapp.core.data.controller

import com.example.securityapp.core.data.firebaseGetSafeCall
import com.example.securityapp.core.data.firebaseUpsertSafeCall
import com.example.securityapp.core.data.repository.ControllerDeviceInController
import com.example.securityapp.core.data.roomSafeFlow
import com.example.securityapp.core.data.sources.FirebaseRemoteDataSource
import com.example.securityapp.utils.Result
import javax.inject.Inject

class ControllerRepository @Inject constructor(
    private val source : FirebaseRemoteDataSource,
    private val controllerDao: ControllerDao
) {
    val collectionId = "controllers"
    suspend fun upsertData(data : ControllerDeviceInController): Result<Unit> {
        return firebaseUpsertSafeCall(
            action = {
                source.addData(documentId = data.email, collectionId = collectionId, data)
            }
        )
    }
    suspend fun getData(email : String) : Result<ControllerDeviceInController?> {
        return firebaseGetSafeCall<ControllerDeviceInController>(
            action = {
                source.get(collectionPath = collectionId, documentId = email)
            }
        )
    }
    fun getFlow() = controllerDao.getFlow()
    suspend fun insertData(data : List<ControllerEntity>): Result<Unit> {
        return roomSafeFlow {
            controllerDao.insert(data)
        }
    }
    suspend fun deleteData(data : List<ControllerEntity>): Result<Unit> {
        return roomSafeFlow {
            controllerDao.delete(data)
        }
    }

}