package com.example.securityapp.modules.controller.presentation.models

sealed interface ControllerTabAction {
    data class OnTabSelected(val index : Int) : ControllerTabAction
    data object OnWipeGallery : ControllerTabAction
    data object OnFactoryReset : ControllerTabAction
    data object OnLocationFetch : ControllerTabAction
    data object OnBlockApps : ControllerTabAction
}