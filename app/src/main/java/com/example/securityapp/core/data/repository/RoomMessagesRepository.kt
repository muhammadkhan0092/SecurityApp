package com.example.securityapp.core.data.repository

import com.example.securityapp.core.data.dao.ControllerMessagesDao
import com.example.securityapp.core.data.ext.roomSafeFlow
import com.example.securityapp.core.data.mappers.mapToMessagesDomain
import com.example.securityapp.core.data.mappers.mapToMessagesEntity
import com.example.securityapp.core.data.models.MessagesEntity
import com.example.securityapp.modules.controller.domain.MessagesDomain
import com.example.securityapp.utils.Result
import com.example.securityapp.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomMessagesRepository @Inject constructor(
    private val controllerMessagesDao: ControllerMessagesDao
) {
    suspend fun upsertData(data: MessagesDomain, email: String): Result<Unit> {
        return roomSafeFlow(
            action = {
                controllerMessagesDao.insert(data.mapToMessagesEntity(email))
            }
        )
    }

    suspend fun getData(email: String): Result<List<MessagesDomain>> {
        return roomSafeFlow<List<MessagesEntity>>(
            action = {
                controllerMessagesDao.getData(email)
            }
        ).map { list->
            list.map {entity->
                entity.mapToMessagesDomain()
            }
        }
    }

    fun getAllFlow(): Flow<List<MessagesDomain>> {
        return controllerMessagesDao.getAllFlow().map { list ->
            list.map {
                it.mapToMessagesDomain()
            }
        }
    }
    fun getFlow(email: String): Flow<List<MessagesDomain>> {
        return controllerMessagesDao.getFlow(email).map { list ->
            list.map {
                it.mapToMessagesDomain()
            }
        }
    }
}