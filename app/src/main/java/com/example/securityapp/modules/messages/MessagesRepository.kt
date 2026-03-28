package com.example.securityapp.modules.messages

import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.controller.domain.models.MessagesDomain
import kotlinx.coroutines.flow.Flow

interface MessagesRepository{
    suspend fun upsertData(data: MessagesDomain, email: String): Result<Unit>
    suspend fun getData(email: String): Result<List<MessagesDomain>>
    suspend fun deleteAllMessages() : Result<Unit>
    fun getAllFlow(): Flow<List<MessagesDomain>>
    fun getFlow(email: String): Flow<List<MessagesDomain>>
}