package com.example.securityapp.modules.controlled.data.repository

import android.util.Log
import com.example.securityapp.core.data.ext.firebaseGetSafeCall
import com.example.securityapp.core.data.ext.firebaseUpsertSafeCall
import com.example.securityapp.core.data.ext.roomSafeFlow
import com.example.securityapp.core.data.mappers.mapToControlledDomain
import com.example.securityapp.core.data.models.ControlledDeviceDto
import com.example.securityapp.core.data.sources.FirebaseRemoteDataSource
import com.example.securityapp.modules.controlled.data.mappers.mapControlledEntityControlledDomain
import com.example.securityapp.modules.controlled.data.mappers.mapToControlledEntity
import com.example.securityapp.modules.controlled.data.source.ControlledDao
import com.example.securityapp.modules.controlled.domain.ControlledDomain
import com.example.securityapp.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ControlledRepository @Inject constructor(
    private val source : FirebaseRemoteDataSource,
    private val controlledDao: ControlledDao
) {
    val collectionId = "controlled"
    suspend fun upsertData(data : ControlledDeviceDto): Result<Unit> {
        return firebaseUpsertSafeCall(
            action = {
                source.addData(documentId = data.email, collectionId = collectionId, data)
            }
        )
    }
    suspend fun getData(email : String) : Result<ControlledDeviceDto?> {
        return firebaseGetSafeCall<ControlledDeviceDto>(
            action = {
                source.get(collectionPath = collectionId, documentId = email)
            }
        )
    }
    val barcodeKey = "barcode"
    suspend fun getDataByBarcode(string: String): Result<ControlledDeviceDto?> {
        return firebaseGetSafeCall<ControlledDeviceDto>(
            action = {
                source.getDocumentByEqualFilter(collectionId, barcodeKey, string)
            }
        )
    }
    fun getFlow(): Flow<List<ControlledDomain>> {
        return controlledDao.getFlow().map { list->
            list.map {
                it.mapControlledEntityControlledDomain()
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
                it.mapControlledEntityControlledDomain()
            }
        }
    }

    fun listenData(email: String): Flow<List<ControlledDomain>?> {
        Log.d("KHAN","COLLECTION ID IS $collectionId")
        Log.d("KHAN","CONTROLLED EMAIL IS $email")
        return source.listenDocument<ControlledDeviceDto>(
            documentId = email,
            collectionPath = collectionId
        ).map { list ->
            list?.controllers?.map {
                it.mapToControlledDomain()
            }
        }
    }
}