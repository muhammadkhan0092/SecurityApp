package com.example.securityapp.modules.permissions.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.modules.permissions.domain.AreAllPermissionsGranted
import com.example.securityapp.modules.permissions.domain.PermissionRepository
import com.example.securityapp.modules.permissions.presentation.models.PermissionAction
import com.example.securityapp.modules.permissions.presentation.models.PermissionEvent
import com.example.securityapp.modules.permissions.presentation.models.PermissionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermissionVm @Inject constructor(
    private val permissionManager: PermissionRepository,
    private val reducer: PermissionReducer
) : ViewModel() {
    private val _state = MutableStateFlow(
        PermissionState(
            isOverlayGranted = permissionManager.hasOverlayPermission(),
            isStorageGranted = permissionManager.hasManageAllFilesPermission(),
            isOtherGranted = permissionManager.areAllRuntimePermissionsGranted(),
            isDefaultAppSet = permissionManager.isDefaultMessageAppSet(),
            isBackgroundPermissionGranted = permissionManager.isBackgroundLocationGranted()
        )
    )
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<PermissionEvent>(replay = 0, extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    fun onAction(action: PermissionAction) {
        val event = when (action) {
            PermissionAction.OnOverlayAction -> {
                permissionManager.requestOverlayPermission()
                return
            }
            PermissionAction.OnStorageAction -> {
                permissionManager.requestManageAllFilesPermission()
                return
            }
            PermissionAction.OnMessageDefaultClicked -> PermissionEvent.RequestMessageDefault
            PermissionAction.OnOtherAction, PermissionAction.OnContinueClicked, PermissionAction.OnBackGroundAction->{
                reducer.actionToEvent(
                    action,state.value
                )?:return
            }
            else -> {
                reducer.actionToState(action,state.value)?.let {newState->
                    _state.update {
                        newState
                    }
                }
                return
            }
        }
        viewModelScope.launch {
            _events.emit(event)
        }
    }

    fun hasOverlayPermission() = permissionManager.hasOverlayPermission()
    fun hasStoragePermission() = permissionManager.hasManageAllFilesPermission()
    fun requestStoragePermission() = permissionManager.requestManageAllFilesPermission()
    fun requestOverlayPermission() = permissionManager.requestOverlayPermission()
    fun isDefaultAppSet() = permissionManager.isDefaultMessageAppSet()
}