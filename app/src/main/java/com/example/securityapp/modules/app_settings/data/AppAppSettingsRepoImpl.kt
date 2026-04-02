package com.example.securityapp.modules.app_settings.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import com.example.securityapp.datastore.AppSettings
import com.example.securityapp.modules.app_settings.domain.AppSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppAppSettingsRepoImpl @Inject constructor(
    private val dataStore: DataStore<
            AppSettings>
) : AppSettingsRepository {

    companion object {
        private const val TAG = "DataStoreRepositoryImplementation"
    }
    private fun Flow<AppSettings>.safeFlowWithDefault(fieldName: String): Flow<AppSettings> {
        return this.catch { e ->
            Log.e(TAG, "Error reading $fieldName", e)
            emit(AppSettings.getDefaultInstance())
        }
    }
    override val email: Flow<String> = dataStore.data
        .safeFlowWithDefault("Current State")
        .map { it.email }

    override val isSetupComplete : Flow<Boolean> = dataStore.data.safeFlowWithDefault("").map { it.isSetupComplete }
    override val barcode : Flow<String> = dataStore.data.safeFlowWithDefault("barcode").map { it.barcode }
    override val userType : Flow<AppSettings.UserType> = dataStore.data.safeFlowWithDefault("barcode").map { it.userType }
    override val shouldBlock : Flow<Boolean> = dataStore.data.safeFlowWithDefault("should_block").map { it.shouldBlock }
    override val number : Flow<String> = dataStore.data.safeFlowWithDefault("number").map { it.number }
    override val packages: Flow<Boolean> = dataStore.data.safeFlowWithDefault("packages").map { it.arePackagesSet }

    override suspend fun getShouldBlock(): Boolean {
        return shouldBlock.first()
    }
    override suspend fun getEmail(): String {
        return email.first()
    }
    suspend fun getBarcode(): String{
        return barcode.first()
    }
    override suspend fun getUserType(): AppSettings.UserType {
        return userType.first()
    }
    override suspend fun getIsSetupCompleted(): Boolean {
        return isSetupComplete.first()
    }

    override suspend fun getIsPackagesSet(): Boolean {
        return packages.first()
    }

    override suspend fun setShouldBlock(state: Boolean): Boolean {
        return try {
            dataStore.updateData { currentData ->
                currentData.toBuilder().setShouldBlock(state).build()
            }
            true
        } catch (e: IOException) {
            Log.e(TAG, "Failed to update is Usage Displayed", e)
            false
        }
    }
    override suspend fun setIsSetupCompleted(state: Boolean): Boolean {
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
    override suspend fun setUserType(state: AppSettings.UserType): Boolean {
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
    override suspend fun setBarcode(state: String): Boolean {
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
    override suspend fun setEmail(state: String): Boolean {
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

    override suspend fun setNumber(state: String): Boolean {
        return try {
            dataStore.updateData { currentData ->
                currentData.toBuilder().setNumber(state).build()
            }
            true
        } catch (e: IOException) {
            Log.e(TAG, "Failed to update is Usage Displayed", e)
            false
        }
    }

    override suspend fun getNumber(): String {
        return number.first()
    }

    override suspend fun setPackagesSet(state : Boolean): Boolean {
        return try {
            dataStore.updateData { currentData ->
                currentData.toBuilder().setArePackagesSet(state).build()
            }
            true
        } catch (e: IOException) {
            Log.e(TAG, "Failed to update is Usage Displayed", e)
            false
        }
    }

}