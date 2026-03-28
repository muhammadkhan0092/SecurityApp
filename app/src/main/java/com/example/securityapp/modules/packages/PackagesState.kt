package com.example.securityapp.modules.packages

data class PackagesState(
    val etValue : String = "",
    val allPackages : List<String>? = null,
    val selectedPackages : List<String> = emptyList()
)