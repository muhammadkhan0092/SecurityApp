package com.example.securityapp.modules.controlled.presentation

sealed interface ControlledAction {
    data class OnTabSelected(val index : Int) : ControlledAction
}