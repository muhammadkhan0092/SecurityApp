package com.example.securityapp.core.data.repository

import com.example.securityapp.core.data.firebaseGetSafeCall
import com.example.securityapp.core.data.firebaseUpsertSafeCall
import com.example.securityapp.core.data.sources.FirebaseRemoteDataSource
import com.example.securityapp.firebase.DtoDevice
import com.example.securityapp.utils.Result
import javax.inject.Inject

class DeviceRepository @Inject constructor(
    private val firebaseRemoteDataSource: FirebaseRemoteDataSource
) {
    private val dtoDeviceCollection = "device"
    suspend fun upsertDevice(data: DtoDevice): Result<Unit> {
        return firebaseUpsertSafeCall {
            firebaseRemoteDataSource.addData(data.barcodeId, "", data)
        }
    }
    suspend fun getDevice(
        barcodeId : String
    ): Result<DtoDevice> {
        return firebaseGetSafeCall<DtoDevice>(
            action = {
                firebaseRemoteDataSource.queryCollection<DtoDevice>(
                    collectionPath = dtoDeviceCollection,
                    documentId = barcodeId
                )!!
            }
        )
    }
    suspend fun insertDeviceInfo(dtoDevice: DtoDevice): Result<Unit> {
        return firebaseUpsertSafeCall(
            action = {
                firebaseRemoteDataSource.addData(documentId = dtoDevice.barcodeId, collectionId = dtoDeviceCollection,dtoDevice)
            }
        )
    }
}