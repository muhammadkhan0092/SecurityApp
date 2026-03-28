package com.example.securityapp.modules.intro.data

import com.example.securityapp.modules.intro.domain.PackageRepository
import com.example.securityapp.modules.packages.PackageModel
import javax.inject.Inject

class AndroidPackageRepository @Inject constructor(
    private val packageSource: PackageSource
) : PackageRepository{
    override fun getInstalledApps(): List<PackageModel> = packageSource.getInstalledApps()

}