package com.example.securityapp.modules.controlled.data.repository

import com.example.securityapp.core.data.ext.firebaseGetSafeCall
import com.example.securityapp.core.data.ext.firebaseUpsertSafeCall
import com.example.securityapp.core.data.ext.roomSafeFlow
import com.example.securityapp.core.data.mappers.mapToControlledDomain
import com.example.securityapp.core.data.models.ControlledDeviceDto
import com.example.securityapp.core.data.sources.FirebaseRemoteDataSource
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.controlled.data.mappers.mapControlledEntityControlledDomain
import com.example.securityapp.modules.controlled.data.mappers.mapToControlledEntity
import com.example.securityapp.modules.controlled.data.source.ControlledDao
import com.example.securityapp.modules.controlled.domain.ControlledDomain
import com.example.securityapp.modules.controlled.domain.repository.ControlledRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FirebaseControlledRepository @Inject constructor(
    private val source : FirebaseRemoteDataSource,
    private val controlledDao: ControlledDao
) : ControlledRepository{
    val collectionId = "controlled"
    override suspend fun upsertData(data : ControlledDeviceDto): Result<Unit> {
        return firebaseUpsertSafeCall(
            action = {
                source.addData(documentId = data.email, collectionId = collectionId, data)
            }
        )
    }
    override suspend fun getData(email : String) : Result<ControlledDeviceDto?> {
        return firebaseGetSafeCall<ControlledDeviceDto>(
            action = {
                source.get(collectionPath = collectionId, documentId = email)
            }
        )
    }
    val barcodeKey = "barcode"
    override suspend fun getDataByBarcode(string: String): Result<ControlledDeviceDto?> {
        return firebaseGetSafeCall<ControlledDeviceDto>(
            action = {
                source.getDocumentByEqualFilter(collectionId, barcodeKey, string)
            }
        )
    }
    override fun getFlow(): Flow<List<ControlledDomain>> {
        return controlledDao.getFlow().map { list->
            list.map {
                it.mapControlledEntityControlledDomain()
            }
        }
    }
    override suspend fun insertData(data : List<ControlledDomain>): Result<Unit> {
        return roomSafeFlow {
            controlledDao.insert(data.map {
                it.mapToControlledEntity()
            })
        }
    }
    override suspend fun deleteData(data : List<ControlledDomain>): Result<Unit> {
        return roomSafeFlow {
            controlledDao.delete(data.map {
                it.mapToControlledEntity()
            })
        }
    }

    override suspend fun getLocalData(): Result<List<ControlledDomain>> {
        return roomSafeFlow {
            controlledDao.getList().map {
                it.mapControlledEntityControlledDomain()
            }
        }
    }

    override fun listenData(email: String): Flow<List<ControlledDomain>?> {
        return source.listenDocument<ControlledDeviceDto>(
            documentId = email,
            collectionPath = collectionId
        ).map { list ->
            list?.controllers?.map {
                it.mapToControlledDomain()
            }
        }
    }

    override suspend fun deleteAll(): Result<Unit> {
        return roomSafeFlow {
            controlledDao.deleteAllControlled()
        }
    }
}