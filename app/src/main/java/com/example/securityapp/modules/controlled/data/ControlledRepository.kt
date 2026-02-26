package com.example.securityapp.modules.controlled.data

import com.example.securityapp.core.data.firebaseGetSafeCall
import com.example.securityapp.core.data.firebaseUpsertSafeCall
import com.example.securityapp.core.data.repository.ControlledDeviceInControlled
import com.example.securityapp.core.data.repository.ControllerDeviceInController
import com.example.securityapp.core.data.repository.mapToControlledDomain
import com.example.securityapp.core.data.roomSafeFlow
import com.example.securityapp.core.data.sources.FirebaseRemoteDataSource
import com.example.securityapp.modules.controlled.domain.ControlledDomain
import com.example.securityapp.modules.controller.data.mapToDomainController
import com.example.securityapp.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ControlledRepository @Inject constructor(
    private val source : FirebaseRemoteDataSource,
    private val controlledDao: ControlledDao
) {
    val collectionId = "controlled"
    suspend fun upsertData(data : ControlledDeviceInControlled): Result<Unit> {
        return firebaseUpsertSafeCall(
            action = {
                source.addData(documentId = data.email, collectionId = collectionId, data)
            }
        )
    }
    suspend fun getData(email : String) : Result<ControlledDeviceInControlled?> {
        return firebaseGetSafeCall<ControlledDeviceInControlled>(
            action = {
                source.get(collectionPath = collectionId, documentId = email)
            }
        )
    }
    val barcodeKey = "barcode"
    suspend fun getDataByBarcode(string: String): Result<ControlledDeviceInControlled?> {
        return firebaseGetSafeCall<ControlledDeviceInControlled>(
            action = {
                source.getDocumentByEqualFilter(collectionId, barcodeKey, string)
            }
        )
    }
    fun getFlow(): Flow<List<ControlledDomain>> {
        return controlledDao.getFlow().map {list->
            list.map {
                it.mapToControlledDomain()
            }
        }
    }
    suspend fun insertData(data : List<ControlledDomain>): Result<Unit> {
        return roomSafeFlow {
            controlledDao.insert(data.map {
                it.mapToControlledEntity()
            })
        }
    }
    suspend fun deleteData(data : List<ControlledDomain>): Result<Unit> {
        return roomSafeFlow {
            controlledDao.delete(data.map {
                it.mapToControlledEntity()
            })
        }
    }

    suspend fun getLocalData(): Result<List<ControlledDomain>> {
        return roomSafeFlow {
            controlledDao.getList().map {
                it.mapToControlledDomain()
            }
        }
    }

    fun listenData(email: String): Flow<List<ControlledDomain>?> {
        return source.listenDocument<ControlledDeviceInControlled>(
            documentId = email,
            collectionPath = collectionId
        ).map { list ->
            list?.controllers?.map {
                it.mapToControlledDomain()
            }
        }
    }
}