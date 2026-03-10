package com.example.securityapp.modules.intro.data

import android.content.Context
import android.content.pm.PackageManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PackageSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getInstalledApps(): List<String> {
        val packageManager = context.packageManager
        val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        val packageNames = mutableListOf<String>()
        for (app in packages) {
            packageNames.add(app.packageName)
        }
        return packageNames
    }
}