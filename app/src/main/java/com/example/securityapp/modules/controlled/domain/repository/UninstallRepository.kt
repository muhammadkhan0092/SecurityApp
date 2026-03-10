package com.example.securityapp.modules.controlled.domain.repository

import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.modules.controlled.domain.models.UninstallDomain
import javax.inject.Inject

interface UninstallRepository {
    suspend fun insertData(data : List<UninstallDomain>) : Result<Unit>
    suspend fun getData() : Result<List<UninstallDomain>>
}