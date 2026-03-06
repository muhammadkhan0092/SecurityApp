package com.example.securityapp.modules.controller.domain.repository

import com.example.securityapp.core.data.models.ControllerDeviceDto
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.controller.domain.models.ControllerDomain
import kotlinx.coroutines.flow.Flow

interface ControllerRepository {
    suspend fun upsertData(data: ControllerDeviceDto): Result<Unit>

    suspend fun getData(email: String): Result<ControllerDeviceDto?>

    fun getFlow(): Flow<List<ControllerDomain>>

    suspend fun insertData(data: List<ControllerDomain>): Result<Unit>

    suspend fun deleteData(data: List<ControllerDomain>): Result<Unit>

    fun listenData(email: String): Flow<List<ControllerDomain>?>

    suspend fun getLocalData(): Result<List<ControllerDomain>>
}