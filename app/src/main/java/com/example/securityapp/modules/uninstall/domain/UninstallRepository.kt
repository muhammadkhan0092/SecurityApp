package com.example.securityapp.modules.uninstall.domain

import com.example.securityapp.core.domain.utils.Result

interface UninstallRepository {
    suspend fun insertData(data : List<UninstallDomainModel>) : Result<Unit>
    suspend fun getData() : Result<List<UninstallDomainModel>>
    fun uninstallApp(packageName: String) : Result<Unit>
    suspend fun deleteAll() : Result<Unit>
}