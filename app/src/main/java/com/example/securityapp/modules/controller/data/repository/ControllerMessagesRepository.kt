package com.example.securityapp.modules.controller.data.repository

import com.example.securityapp.core.data.roomSafeFlow
import com.example.securityapp.modules.controller.data.dao.ControllerMessagesDao
import com.example.securityapp.modules.controller.data.mappers.mapToControllerMessagesDomain
import com.example.securityapp.modules.controller.data.mappers.mapToControllerMessagesEntity
import com.example.securityapp.modules.controller.data.models.ControllerMessagesEntity
import com.example.securityapp.modules.controller.domain.ControllerMessagesDomain
import com.example.securityapp.utils.Result
import com.example.securityapp.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ControllerMessagesRepository @Inject constructor(
    private val controllerMessagesDao: ControllerMessagesDao
) {
    suspend fun upsertData(data: ControllerMessagesDomain, email: String): Result<Unit> {
        return roomSafeFlow(
            action = {
                controllerMessagesDao.insert(data.mapToControllerMessagesEntity(email))
            }
        )
    }

    suspend fun getData(email: String): Result<List<ControllerMessagesDomain>> {
        return roomSafeFlow<List<ControllerMessagesEntity>>(
            action = {
                controllerMessagesDao.getData(email)
            }
        ).map{list->
            list.map {entity->
                entity.mapToControllerMessagesDomain()
            }
        }
    }

    fun getFlow(): Flow<List<ControllerMessagesDomain>> {
        return controllerMessagesDao.getFlow().map { list ->
            list.map {
                it.mapToControllerMessagesDomain()
            }
        }
    }
}