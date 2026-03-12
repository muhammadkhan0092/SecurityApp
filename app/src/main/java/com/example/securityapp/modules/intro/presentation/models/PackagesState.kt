package com.example.securityapp.modules.intro.presentation.models

data class PackagesState(
    val etValue : String = "",
    val allPackages : List<String>? = null,
    val selectedPackages : List<String> = emptyList()
)