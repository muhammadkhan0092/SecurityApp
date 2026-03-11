package com.example.securityapp.core.domain.repository

import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.controlled.domain.models.UninstallDomain

interface UninstallRepository {
    suspend fun insertData(data : List<UninstallDomain>) : Result<Unit>
    suspend fun getData() : Result<List<UninstallDomain>>
    fun uninstallApp(packageName: String) : Result<Unit>
}