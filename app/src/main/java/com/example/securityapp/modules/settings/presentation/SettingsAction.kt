package com.example.securityapp.modules.settings.presentation

sealed interface SettingsAction {
    data object OnLogoutClicked : SettingsAction
}