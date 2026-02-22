package com.example.securityapp.core.data.repository

import com.example.securityapp.core.data.firebaseGetSafeCall
import com.example.securityapp.core.data.firebaseUpsertSafeCall
import com.example.securityapp.core.data.sources.FirebaseRemoteDataSource
import com.example.securityapp.firebase.DtoControllerUser
import com.example.securityapp.utils.Result
import javax.inject.Inject

class ControllerUserRepository @Inject constructor(
    private val source : FirebaseRemoteDataSource
) {
    private val collectionId = "controller_user"
    suspend fun createUser(email : String,password : String): Result<Unit> {
        return firebaseUpsertSafeCall(
            action = {
                source.addData(documentId = email, collectionId = collectionId, DtoControllerUser(email,password))
            }
        )
    }
    suspend fun getUser(email : String) : Result<DtoControllerUser>{
        return firebaseGetSafeCall<DtoControllerUser>(
            action = {
                source.queryCollection<DtoControllerUser>(documentId = email, collectionPath = collectionId)
            }
        )
    }
}