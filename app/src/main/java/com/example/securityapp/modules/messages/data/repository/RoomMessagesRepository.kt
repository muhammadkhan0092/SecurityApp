package com.example.securityapp.modules.messages.data.repository

import com.example.securityapp.core.data.ext.roomSafeFlow
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.core.domain.utils.map
import com.example.securityapp.modules.messages.domain.models.MessagesDomain
import com.example.securityapp.modules.messages.data.ControllerMessagesDao
import com.example.securityapp.modules.messages.data.MessagesEntity
import com.example.securityapp.modules.messages.data.mapToMessagesDomain
import com.example.securityapp.modules.messages.data.mapToMessagesEntity
import com.example.securityapp.modules.messages.domain.repository.MessagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomMessagesRepository @Inject constructor(
    private val controllerMessagesDao: ControllerMessagesDao
) : MessagesRepository {
    override suspend fun upsertData(data: MessagesDomain, email: String): Result<Unit> {
        return roomSafeFlow(
            action = {
                controllerMessagesDao.insert(data.mapToMessagesEntity(email))
            }
        )
    }

    override suspend fun getData(email: String): Result<List<MessagesDomain>> {
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

    override suspend fun deleteAllMessages(): Result<Unit> {
        return roomSafeFlow {
            controllerMessagesDao.deleteAll()
        }
    }

    override fun getAllFlow(): Flow<List<MessagesDomain>> {
        return controllerMessagesDao.getAllFlow().map { list ->
            list.map {
                it.mapToMessagesDomain()
            }
        }
    }
    override fun getFlow(email: String): Flow<List<MessagesDomain>> {
        return controllerMessagesDao.getFlow(email).map { list ->
            list.map {
                it.mapToMessagesDomain()
            }
        }
    }
}