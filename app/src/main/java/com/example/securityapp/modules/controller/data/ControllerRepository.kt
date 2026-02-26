package com.example.securityapp.modules.controller.data

import com.example.securityapp.core.data.firebaseGetSafeCall
import com.example.securityapp.core.data.firebaseUpsertSafeCall
import com.example.securityapp.core.data.repository.ControllerDeviceInController
import com.example.securityapp.core.data.repository.mapToControllerDomain
import com.example.securityapp.core.data.roomSafeFlow
import com.example.securityapp.core.data.sources.FirebaseRemoteDataSource
import com.example.securityapp.modules.controlled.domain.ControlledDomain
import com.example.securityapp.modules.controller.domain.ControllerDomain
import com.example.securityapp.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ControllerRepository @Inject constructor(
    private val source: FirebaseRemoteDataSource,
    private val controllerDao: ControllerDao
) {
    val collectionId = "controllers"
    suspend fun upsertData(data: ControllerDeviceInController): Result<Unit> {
        return firebaseUpsertSafeCall(
            action = {
                source.addData(documentId = data.email, collectionId = collectionId, data)
            }
        )
    }

    suspend fun getData(email: String): Result<ControllerDeviceInController?> {
        return firebaseGetSafeCall<ControllerDeviceInController>(
            action = {
                source.get(collectionPath = collectionId, documentId = email)
            }
        )
    }

    fun getFlow(): Flow<List<ControllerDomain>> {
        return controllerDao.getFlow().map { list ->
            list.map {
                it.mapToDomainController()
            }
        }
    }

    suspend fun insertData(data: List<ControllerDomain>): Result<Unit> {
        return roomSafeFlow {
            controllerDao.insert(data.map {
                it.mapToControllerEntity()
            })
        }
    }

    suspend fun deleteData(data: List<ControllerDomain>): Result<Unit> {
        return roomSafeFlow {
            controllerDao.delete(data.map {
                it.mapToControllerEntity()
            })
        }
    }

    fun listenData(email: String): Flow<List<ControllerDomain>?> {
        return source.listenDocument<ControllerDeviceInController>(
            documentId = email,
            collectionPath = collectionId
        ).map { list ->
            list?.devices?.map {
                it.mapToControllerDomain()
            }
        }
    }

    suspend fun getLocalData(): Result<List<ControllerDomain>> {
        return roomSafeFlow {
            controllerDao.getData().map {
                it.mapToDomainController()
            }
        }
    }
}