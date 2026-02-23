package com.example.securityapp.core.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import com.example.securityapp.datastore.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImplementation @Inject constructor(
    private val dataStore: DataStore<AppSettings>
){

    companion object {
        private const val TAG = "DataStoreRepositoryImplementation"
    }
    private fun Flow<AppSettings>.safeFlowWithDefault(fieldName: String): Flow<AppSettings> {
        return this.catch { e ->
            Log.e(TAG, "Error reading $fieldName", e)
            emit(AppSettings.getDefaultInstance())
        }
    }
    val email: Flow<String> = dataStore.data
        .safeFlowWithDefault("Current State")
        .map { it.email }

    val isSetupComplete : Flow<Boolean> = dataStore.data.safeFlowWithDefault("").map { it.isSetupComplete }
    val barcode : Flow<String> = dataStore.data.safeFlowWithDefault("barcode").map { it.barcode }
    val userType : Flow<AppSettings.UserType> = dataStore.data.safeFlowWithDefault("barcode").map { it.userType }

    suspend fun getEmail(): String {
        return email.first()
    }
    suspend fun getUserType(): AppSettings.UserType {
        return userType.first()
    }
    suspend fun getIsSetupCompleted(): Boolean {
        return isSetupComplete.first()
    }
    suspend fun setIsSetupCompleted(state: Boolean): Boolean {
        return try {
            dataStore.updateData { currentData ->
                currentData.toBuilder().setIsSetupComplete(state).build()
            }
            true
        } catch (e: IOException) {
            Log.e(TAG, "Failed to update is Usage Displayed", e)
            false
        }
    }
    suspend fun setUserType(state: AppSettings.UserType): Boolean {
        return try {
            dataStore.updateData { currentData ->
                currentData.toBuilder().setUserType(state).build()
            }
            true
        } catch (e: IOException) {
            Log.e(TAG, "Failed to update is Usage Displayed", e)
            false
        }
    }
    suspend fun setBarcode(state: String): Boolean {
        return try {
            dataStore.updateData { currentData ->
                currentData.toBuilder().setBarcode(state).build()
            }
            true
        } catch (e: IOException) {
            Log.e(TAG, "Failed to update is Usage Displayed", e)
            false
        }
    }
    suspend fun setEmail(state: String): Boolean {
        return try {
            dataStore.updateData { currentData ->
                currentData.toBuilder().setEmail(state).build()
            }
            true
        } catch (e: IOException) {
            Log.e(TAG, "Failed to update is Usage Displayed", e)
            false
        }
    }
}