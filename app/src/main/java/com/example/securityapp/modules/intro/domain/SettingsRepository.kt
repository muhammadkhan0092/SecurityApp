package com.example.securityapp.modules.intro.domain

import com.example.securityapp.datastore.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface SettingsRepository {

    val email: Flow<String>
    val isSetupComplete : Flow<Boolean>
    val barcode : Flow<String>
    val userType : Flow<AppSettings.UserType>
    val shouldBlock : Flow<Boolean>
    val number : Flow<String>
    val packages : Flow<Boolean>
    suspend fun getShouldBlock(): Boolean {
        return shouldBlock.first()
    }
    suspend fun getEmail(): String {
        return email.first()
    }
    suspend fun getUserType(): AppSettings.UserType {
        return userType.first()
    }
    suspend fun getIsSetupCompleted(): Boolean {
        return isSetupComplete.first()
    }
    suspend fun getIsPackagesSet(): Boolean

    suspend fun setShouldBlock(state: Boolean): Boolean
    suspend fun setIsSetupCompleted(state: Boolean): Boolean
    suspend fun setUserType(state: AppSettings.UserType): Boolean
    suspend fun setBarcode(state: String): Boolean
    suspend fun setEmail(state: String): Boolean
    suspend fun setNumber(state: String): Boolean
    suspend fun getNumber(): String
    suspend fun setPackagesSet(state : Boolean = true): Boolean
}