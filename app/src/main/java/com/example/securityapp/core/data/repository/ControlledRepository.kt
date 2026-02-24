package com.example.securityapp.core.data.repository

import com.example.securityapp.core.data.firebaseGetSafeCall
import com.example.securityapp.core.data.firebaseUpsertSafeCall
import com.example.securityapp.core.data.sources.FirebaseRemoteDataSource
import com.example.securityapp.utils.Result
import javax.inject.Inject

class ControlledRepository @Inject constructor(
    private val source : FirebaseRemoteDataSource
) {
    val collectionId = "controlled"
    suspend fun upsertData(data : ControlledDeviceInControlled): Result<Unit> {
        return firebaseUpsertSafeCall(
            action = {
                source.addData(documentId =data.email, collectionId = collectionId,data)
            }
        )
    }
    suspend fun getData(email : String) : Result<ControlledDeviceInControlled?> {
        return  firebaseGetSafeCall<ControlledDeviceInControlled>(
            action = {
                source.get(collectionPath = collectionId, documentId = email)
            }
        )
    }
    val barcodeKey = "barcode"
    suspend fun getDataByBarcode(string: String): Result<ControlledDeviceInControlled?> {
        return firebaseGetSafeCall<ControlledDeviceInControlled>(
            action = {
                source.getDocumentByEqualFilter(collectionId,barcodeKey,string)
            }
        )
    }
}