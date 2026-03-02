package com.example.securityapp.modules.controlled.presentation.models

sealed interface ControlledAction {
    data class OnTabSelected(val index : Int) : ControlledAction
    data class OnDeleteClicked(val email : String) : ControlledAction
}