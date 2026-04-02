package com.example.securityapp.modules.permissions.presentation.ui

import com.example.securityapp.modules.permissions.domain.AreAllPermissionsGranted
import com.example.securityapp.modules.permissions.domain.PermissionRepository
import com.example.securityapp.modules.permissions.presentation.models.PermissionAction
import com.example.securityapp.modules.permissions.presentation.models.PermissionEvent
import com.example.securityapp.modules.permissions.presentation.models.PermissionState
import javax.inject.Inject

class PermissionReducer @Inject constructor(
    private val areAllPermissionsGranted: AreAllPermissionsGranted,
    private val permissionRepository: PermissionRepository
) {
    fun actionToState(action: PermissionAction, state: PermissionState): PermissionState? {
        return when (action) {
            PermissionAction.OnOtherPermissionGranted -> {
                state.copy(isOtherGranted = true)
            }

            PermissionAction.OnOverlayPermissionGranted -> {
                state.copy(isOverlayGranted = true)
            }

            PermissionAction.OnStoragePermissionGranted -> {
                state.copy(isStorageGranted = true)
            }

            is PermissionAction.DefaultAppResult -> {
                state.copy(isDefaultAppSet = action.bool)
            }

            PermissionAction.OnBackgroundPermissionGranted -> {
                state.copy(isBackgroundPermissionGranted = true)
            }
            else -> null
        }
    }

    fun actionToEvent(
        action: PermissionAction,
        state: PermissionState
    ): PermissionEvent? {
        val runtimePermissions = permissionRepository.getAllRuntimePermissions()
        val locationPermissions = permissionRepository.getBackgroundLocationPermission()
        val areAllRuntimeGranted = permissionRepository.areAllRuntimePermissionsGranted()
        return when(action){
            PermissionAction.OnOtherAction -> {
                if (!state.isOtherGranted) {
                    PermissionEvent.RequestOtherPermission(runtimePermissions)
                } else null
            }
            PermissionAction.OnContinueClicked-> {
                when{
                    areAllPermissionsGranted()-> PermissionEvent.NavigateToIntro
                    else -> PermissionEvent.Toast("Grant All Permissions")
                }
            }
            PermissionAction.OnBackGroundAction -> {
                if(areAllRuntimeGranted) PermissionEvent.RequestBackgroundLocationPermission(locationPermissions)
                else PermissionEvent.Toast("Grant Other Permissions First")
            }
            else -> null
        }
    }
}