package com.example.securityapp.core.domain.repository

import com.example.securityapp.modules.controller.domain.models.MessagesDomain
import com.example.securityapp.core.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface MessagesRepository{
    suspend fun upsertData(data: MessagesDomain, email: String): Result<Unit>
    suspend fun getData(email: String): Result<List<MessagesDomain>>
    fun getAllFlow(): Flow<List<MessagesDomain>>
    fun getFlow(email: String): Flow<List<MessagesDomain>>
}