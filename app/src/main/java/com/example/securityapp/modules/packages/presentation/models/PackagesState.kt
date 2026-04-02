package com.example.securityapp.modules.packages.presentation.models

data class PackagesState(
    val etValue : String = "",
    val allPackages : List<String>? = null,
    val selectedPackages : List<String> = emptyList(),
    val fullStringList : List<String> = emptyList(),
    val fullPackageModel : List<PackageModel> = emptyList()
)