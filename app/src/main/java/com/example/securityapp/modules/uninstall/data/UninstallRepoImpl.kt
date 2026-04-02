package com.example.securityapp.modules.uninstall.data

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.securityapp.core.data.ext.roomSafeFlow
import com.example.securityapp.core.domain.utils.Result
import com.example.securityapp.core.domain.utils.map
import com.example.securityapp.modules.uninstall.domain.UninstallDomainModel
import com.example.securityapp.modules.uninstall.domain.UninstallRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UninstallRepoImpl @Inject constructor(
    private val uninstallDao: UninstallDao,
    @ApplicationContext private val context: Context
) : UninstallRepository {
    override suspend fun insertData(data: List<UninstallDomainModel>): Result<Unit> {
        return roomSafeFlow(
            action = {
                uninstallDao.upsert(
                    data.map { it.uninstallDomainToUninstallEntity() }
                )
            }
        )
    }

    override suspend fun getData(): Result<List<UninstallDomainModel>> {
        return roomSafeFlow<List<UninstallDataModel>>(action = { uninstallDao.getList() }).map { list ->
            list.map {
                it.uninstallEntityToUninstallDomain()
            }
        }
    }

    override fun uninstallApp(packageName: String): Result<Unit> {
        return try {
            val CODE_UNINSTALL_RESULT = 1235
            val ACTION_UNINSTALL_RESULT = "eu.sisik.removehideaps.ACTION_UNINSTALL_RESULT"
            val intentSender = PendingIntent.getBroadcast(
                context,
                CODE_UNINSTALL_RESULT,
                Intent(ACTION_UNINSTALL_RESULT),
                PendingIntent.FLAG_IMMUTABLE
            ).intentSender
            val pi = context.packageManager.packageInstaller
            pi.uninstall(packageName, intentSender)
            Result.Success(Unit)
        }
        catch (e : Exception){
            Result.Error(e.message?:"Unknown Error")
        }
    }

    override suspend fun deleteAll(): Result<Unit> {
        return roomSafeFlow {
            uninstallDao.deleteAll()
        }
    }
}