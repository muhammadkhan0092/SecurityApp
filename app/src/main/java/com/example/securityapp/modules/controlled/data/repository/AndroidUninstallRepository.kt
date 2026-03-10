package com.example.securityapp.modules.controlled.data.repository

import com.example.securityapp.core.data.ext.roomSafeFlow
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.core.domain.utils.map
import com.example.securityapp.modules.controlled.data.mappers.uninstallDomainToUninstallEntity
import com.example.securityapp.modules.controlled.data.mappers.uninstallEntityToUninstallDomain
import com.example.securityapp.modules.controlled.data.models.UninstallEntity
import com.example.securityapp.modules.controlled.data.source.UninstallDao
import com.example.securityapp.modules.controlled.domain.models.UninstallDomain
import com.example.securityapp.modules.controlled.domain.repository.UninstallRepository
import javax.inject.Inject

class AndroidUninstallRepository @Inject constructor(
    private val uninstallDao: UninstallDao
) : UninstallRepository{
    override suspend fun insertData(data: List<UninstallDomain>): Result<Unit> {
        return roomSafeFlow(
            action = {
                uninstallDao.upsert(
                    data.map { it.uninstallDomainToUninstallEntity() }
                )
            }
        )
    }

    override suspend fun getData(): Result<List<UninstallDomain>> {
        return roomSafeFlow<List<UninstallEntity>>(action = {uninstallDao.getList()}).map {list->
            list.map {
                it.uninstallEntityToUninstallDomain()
            }
        }
    }
}