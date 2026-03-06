package com.example.securityapp.modules.controlled.domain.repository

import com.example.securityapp.core.data.models.ControlledDeviceDto
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.controlled.domain.ControlledDomain
import kotlinx.coroutines.flow.Flow

interface ControlledRepository  {
    suspend fun upsertData(data : ControlledDeviceDto): Result<Unit>
    suspend fun getData(email : String) : Result<ControlledDeviceDto?>
    suspend fun getDataByBarcode(string: String): Result<ControlledDeviceDto?>
    fun getFlow(): Flow<List<ControlledDomain>>
    suspend fun insertData(data : List<ControlledDomain>): Result<Unit>
    suspend fun deleteData(data : List<ControlledDomain>): Result<Unit>
    suspend fun getLocalData(): Result<List<ControlledDomain>>
    fun listenData(email: String): Flow<List<ControlledDomain>?>
}