package com.example.securityapp.modules.intro.presentation.models

sealed interface PackagesAction {
    data class OnPackageClicked(val name : String) : PackagesAction
    data object OnNextClicked : PackagesAction
    data class OnTextChanged(val text : String) : PackagesAction
}