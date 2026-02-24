package com.example.securityapp.core.data.repository

import com.example.securityapp.core.data.firebaseGetSafeCall
import com.example.securityapp.core.data.firebaseUpsertSafeCall
import com.example.securityapp.core.data.sources.FirebaseRemoteDataSource
import com.example.securityapp.core.data.toDomainDevice
import com.example.securityapp.core.data.toDtoDevice
import com.example.securityapp.domain.DomainDevice
import com.example.securityapp.firebase.DtoDevice
import com.example.securityapp.utils.Result
import com.example.securityapp.utils.map
import javax.inject.Inject

class DeviceRepository @Inject constructor(
    private val firebaseRemoteDataSource: FirebaseRemoteDataSource
) {
    private val dtoDeviceCollection = "device"
    suspend fun upsertDevice(data: DomainDevice): Result<Unit> {
        return firebaseUpsertSafeCall {
            firebaseRemoteDataSource.addData(data.barcodeId, "", data.toDtoDevice())
        }
    }
    suspend fun getDevice(
        email : String
    ): Result<DomainDevice?> {
        val result = firebaseGetSafeCall<DtoDevice>(
            action = {
                firebaseRemoteDataSource.queryCollection<DtoDevice>(
                    collectionPath = dtoDeviceCollection,
                    documentId = email
                )
            }
        )
        return result.map {
            it?.toDomainDevice()
        }
    }
    suspend fun insertDeviceInfo(device: DomainDevice): Result<Unit> {
        return firebaseUpsertSafeCall(
            action = {
                firebaseRemoteDataSource.addData(documentId = device.email, collectionId = dtoDeviceCollection,device.toDtoDevice())
            }
        )
    }
}