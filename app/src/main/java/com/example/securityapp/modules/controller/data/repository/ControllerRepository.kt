package com.example.securityapp.modules.controller.data.repository

import com.example.securityapp.core.data.ext.firebaseGetSafeCall
import com.example.securityapp.core.data.ext.firebaseUpsertSafeCall
import com.example.securityapp.core.data.ext.roomSafeFlow
import com.example.securityapp.core.data.mappers.mapToControllerDomain
import com.example.securityapp.core.data.models.ControllerDeviceDto
import com.example.securityapp.core.data.sources.FirebaseRemoteDataSource
import com.example.securityapp.modules.controller.data.dao.ControllerDao
import com.example.securityapp.modules.controller.data.mappers.mapToControllerEntity
import com.example.securityapp.modules.controller.data.mappers.mapToDomainController
import com.example.securityapp.modules.controller.domain.models.ControllerDomain
import com.example.securityapp.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ControllerRepository @Inject constructor(
    private val source: FirebaseRemoteDataSource,
    private val controllerDao: ControllerDao
) {
    val collectionId = "controllers"
    suspend fun upsertData(data: ControllerDeviceDto): Result<Unit> {
        return firebaseUpsertSafeCall(
            action = {
                source.addData(documentId = data.email, collectionId = collectionId, data)
            }
        )
    }

    suspend fun getData(email: String): Result<ControllerDeviceDto?> {
        return firebaseGetSafeCall<ControllerDeviceDto>(
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
        return source.listenDocument<ControllerDeviceDto>(
            documentId = email,
            collectionPath = collectionId
        ).map { list ->
            list?.controlled?.map {
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