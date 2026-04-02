package com.example.securityapp.modules.packages.data

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.example.securityapp.modules.packages.domain.PackageRepository
import com.example.securityapp.modules.packages.presentation.models.PackageModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidPackageRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : PackageRepository {
    override fun getInstalledApps(): List<PackageModel> {
        val packageManager = context.packageManager
        val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        return packages
            .filter { app ->
                val isSystemApp = (app.flags and ApplicationInfo.FLAG_SYSTEM) != 0
                val isMyApp = app.packageName == context.packageName
                !isSystemApp && !isMyApp
            }
            .map { app ->
                val appName = packageManager.getApplicationLabel(app).toString()

                PackageModel(
                    name = appName,
                    packageName = app.packageName
                )
            }
    }

}