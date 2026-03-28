package com.example.securityapp.modules.settings

sealed interface SettingsAction {
    data object OnLogoutClicked : SettingsAction
}