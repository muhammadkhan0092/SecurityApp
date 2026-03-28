package com.example.securityapp.modules.permissions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val permissionManager: PermissionManager
) : ViewModel() {
    private val _state = MutableStateFlow(
        PermissionState(
            isOverlayGranted = permissionManager.hasOverlayPermission(),
            isStorageGranted = permissionManager.hasManageAllFilesPermission(),
            isOtherGranted = permissionManager.areAllRuntimePermissionsGranted(),
            isDefaultAppSet = permissionManager.isDefaultMessageAppSet()
        )
    )
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<PermissionEvent>(replay = 0, extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    fun onAction(action: PermissionAction) {
        val event = when (action) {
            PermissionAction.OnOtherAction -> {
                if (!_state.value.isOtherGranted) {
                    PermissionEvent.RequestOtherPermission
                } else return
            }

            PermissionAction.OnOverlayAction -> {
                PermissionEvent.RequestOverlayPermission
            }

            PermissionAction.OnStorageAction -> {
                PermissionEvent.RequestStoragePermission
            }

            PermissionAction.OnOtherPermissionGranted -> {
                _state.update {
                    it.copy(isOtherGranted = true)
                }
                return
            }

            PermissionAction.OnOverlayPermissionGranted -> {
                _state.update {
                    it.copy(isOverlayGranted = true)
                }
                return
            }

            PermissionAction.OnStoragePermissionGranted -> {
                _state.update {
                    it.copy(isStorageGranted = true)
                }
                return
            }

            PermissionAction.OnContinueClicked -> {
                when{
                    state.value.isOtherGranted && state.value.isStorageGranted && state.value.isOverlayGranted && state.value.isDefaultAppSet->  PermissionEvent.NavigateToIntro
                    else ->PermissionEvent.Toast("Grant All Permissions")
                }
            }
            is PermissionAction.DefaultAppResult -> {
                _state.update {
                    it.copy(isDefaultAppSet = action.bool)
                }
                return
            }
            PermissionAction.OnMessageDefaultClicked -> PermissionEvent.RequestMessageDefault
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