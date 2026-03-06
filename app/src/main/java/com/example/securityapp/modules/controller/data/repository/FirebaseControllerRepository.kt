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
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.controller.domain.repository.ControllerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FirebaseControllerRepository @Inject constructor(
    private val source: FirebaseRemoteDataSource,
    private val controllerDao: ControllerDao
)  : ControllerRepository{
    val collectionId = "controllers"
    override suspend fun upsertData(data: ControllerDeviceDto): Result<Unit> {
        return firebaseUpsertSafeCall(
            action = {
                source.addData(documentId = data.email, collectionId = collectionId, data)
            }
        )
    }

    override suspend fun getData(email: String): Result<ControllerDeviceDto?> {
        return firebaseGetSafeCall<ControllerDeviceDto>(
            action = {
                source.get(collectionPath = collectionId, documentId = email)
            }
        )
    }

    override fun getFlow(): Flow<List<ControllerDomain>> {
        return controllerDao.getFlow().map { list ->
            list.map {
                it.mapToDomainController()
            }
        }
    }

    override suspend fun insertData(data: List<ControllerDomain>): Result<Unit> {
        return roomSafeFlow {
            controllerDao.insert(data.map {
                it.mapToControllerEntity()
            })
        }
    }

    override suspend fun deleteData(data: List<ControllerDomain>): Result<Unit> {
        return roomSafeFlow {
            controllerDao.delete(data.map {
                it.mapToControllerEntity()
            })
        }
    }

    override fun listenData(email: String): Flow<List<ControllerDomain>?> {
        return source.listenDocument<ControllerDeviceDto>(
            documentId = email,
            collectionPath = collectionId
        ).map { list ->
            list?.controlled?.map {
                it.mapToControllerDomain()
            }
        }
    }

    override suspend fun getLocalData(): Result<List<ControllerDomain>> {
        return roomSafeFlow {
            controllerDao.getData().map {
                it.mapToDomainController()
            }
        }
    }
}