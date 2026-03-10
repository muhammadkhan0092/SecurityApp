package com.example.securityapp.modules.intro.data

import com.example.securityapp.modules.intro.domain.PackageRepository
import javax.inject.Inject

class AndroidPackageRepository @Inject constructor(
    private val packageSource: PackageSource
) : PackageRepository{
    override fun getInstalledApps(): List<String> = packageSource.getInstalledApps()

}