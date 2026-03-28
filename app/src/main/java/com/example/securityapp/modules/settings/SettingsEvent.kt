package com.example.securityapp.modules.settings

sealed interface SettingsEvent {
    data object NavigateToIntro : SettingsEvent
}