package com.example.securityapp.permissions

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.permissions.SpecialPermissions.hasManageAllFilesPermission
import com.example.securityapp.permissions.SpecialPermissions.hasOverlayPermission
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermissionVm @Inject constructor(
    @ApplicationContext private val context: Context,
    private val permissionManager: PermissionManager
) : ViewModel() {
    private val _state = MutableStateFlow(
        PermissionState(
            isOverlayGranted = hasOverlayPermission(context),
            isStorageGranted = hasManageAllFilesPermission(),
            isOtherGranted = permissionManager.areAllRuntimePermissionsGranted()
        )
    )
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<PermissionEvent>(replay = 0, extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    fun onAction(action: PermissionAction) {
        val event = when (action) {
            PermissionAction.OnOtherAction -> {
                Log.d("KHAN", "ON OTHER CLICKED IN VM")
                if (!_state.value.isOtherGranted) {
                    Log.d("KHAN", "IN OTHER IF")
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
            PermissionAction.OnContinueClicked ->{
                when{
                    state.value.isOtherGranted && state.value.isStorageGranted && state.value.isOverlayGranted->  PermissionEvent.NavigateToIntro
                    else ->PermissionEvent.Toast("Grant All Permissions")
                }
            }
        }
        viewModelScope.launch {
            _events.emit(event)
        }
    }
}