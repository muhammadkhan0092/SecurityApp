package com.example.securityapp.modules.controller.presentation.models

sealed interface ControllerTabAction {
    data class OnTabSelected(val index : Int) : ControllerTabAction
    data class OnWipeGallery(val number : String) : ControllerTabAction
    data class OnFactoryReset(val number : String) : ControllerTabAction
    data class OnLocationFetch(val number : String) : ControllerTabAction
    data class OnBlockApps(val number : String) : ControllerTabAction
    data class OnUninstallClicked(val number: String) : ControllerTabAction
}