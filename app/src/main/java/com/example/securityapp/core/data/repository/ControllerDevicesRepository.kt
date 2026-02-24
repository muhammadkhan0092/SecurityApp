package com.example.securityapp.core.data.repository

import com.example.securityapp.core.data.DomainControllerDevices
import com.example.securityapp.core.data.firebaseListSafeCall
import com.example.securityapp.core.data.firebaseUpsertSafeCall
import com.example.securityapp.core.data.sources.FirebaseRemoteDataSource
import com.example.securityapp.utils.Result
import javax.inject.Inject

class ControllerDevicesRepository @Inject constructor(
    private val source : FirebaseRemoteDataSource
) {
    val collectionId = "controlled_devices"
    suspend fun upsertControlledDevice(documentId:String,controlledDevice: DomainControlledDevice): Result<Unit> {
        return firebaseUpsertSafeCall(
            action = {
                source.addData(documentId =documentId, collectionId = email,controllerDevices)
            }
        )
    }
    suspend fun getControllerDevices(email : String) : Result<List<DomainControllerDevices>>{
        return  firebaseListSafeCall<DomainControllerDevices>(
            action = {
                source.getAllDocuments(email)
            }
        )
    }
}