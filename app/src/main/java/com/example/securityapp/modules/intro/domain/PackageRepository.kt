package com.example.securityapp.modules.intro.domain

import com.example.securityapp.modules.packages.PackageModel

interface PackageRepository{
    fun getInstalledApps(): List<PackageModel>
}