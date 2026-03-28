package com.example.securityapp.modules.controlled.presentation.models

sealed interface ControlledAction {
    object OnSettingsClicked : ControlledAction
    data class OnTabSelected(val index : Int) : ControlledAction
    data class OnDeleteClicked(val email : String) : ControlledAction
}