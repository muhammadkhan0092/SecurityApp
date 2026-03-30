package com.example.securityapp.modules.settings.presentation

sealed interface SettingsEvent {
    data object NavigateToIntro : SettingsEvent
}