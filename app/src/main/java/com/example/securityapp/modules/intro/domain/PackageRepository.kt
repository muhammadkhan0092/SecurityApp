package com.example.securityapp.modules.intro.domain

interface PackageRepository{
    fun getInstalledApps(): List<String>
}