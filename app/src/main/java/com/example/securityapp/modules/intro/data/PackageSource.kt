package com.example.securityapp.modules.intro.data

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.example.securityapp.modules.packages.PackageModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PackageSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getInstalledApps(): List<PackageModel> {
        val packageManager = context.packageManager
        val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        return packages
            .filter { app ->
                // ✅ 1. Exclude system apps
                val isSystemApp = (app.flags and android.content.pm.ApplicationInfo.FLAG_SYSTEM) != 0

                // ✅ 2. Exclude your own app
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