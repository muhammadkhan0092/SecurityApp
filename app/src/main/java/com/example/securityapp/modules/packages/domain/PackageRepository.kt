package com.example.securityapp.modules.packages.domain

import com.example.securityapp.modules.packages.presentation.models.PackageModel

interface PackageRepository{
    fun getInstalledApps(): List<PackageModel>
}