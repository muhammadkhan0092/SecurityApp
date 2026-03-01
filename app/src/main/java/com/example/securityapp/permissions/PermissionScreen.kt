package com.example.securityapp.permissions

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.securityapp.permissions.SpecialPermissions.hasManageAllFilesPermission
import com.example.securityapp.permissions.SpecialPermissions.hasOverlayPermission
import com.example.securityapp.permissions.SpecialPermissions.requestManageAllFilesPermission
import com.example.securityapp.permissions.SpecialPermissions.requestOverlayPermission
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PermissionScreen(
    state: PermissionState,
    events : SharedFlow<PermissionEvent>,
    onAction : (PermissionAction)-> Unit,
    onContinueEvent :()-> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val allGranted = result.values.all { it }
        if (allGranted) {
            Log.d("Permission", "All runtime permissions granted")
            onAction(PermissionAction.OnOtherPermissionGranted)
        } else {
            Log.d("Permission", "Some permissions denied")
        }
    }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit){
        events.collectLatest {
            when(it){
                PermissionEvent.RequestOtherPermission -> launcher.launch(RuntimePermissions.permissions)
                PermissionEvent.RequestOverlayPermission -> requestOverlayPermission(context)
                PermissionEvent.RequestStoragePermission -> requestManageAllFilesPermission(context)
                PermissionEvent.NavigateToIntro -> onContinueEvent()
                is PermissionEvent.Toast -> Toast.makeText(context, it.str, Toast.LENGTH_SHORT).show()
            }
        }
    }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (hasOverlayPermission(context)) {
                    onAction(PermissionAction.OnOverlayPermissionGranted)
                }
                if (hasManageAllFilesPermission()) {
                    onAction(PermissionAction.OnStoragePermissionGranted)
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    LaunchedEffect(Unit) {
        if(hasManageAllFilesPermission()){
            onAction(PermissionAction.OnStoragePermissionGranted)
        }
        if(hasOverlayPermission(context)){
            onAction(PermissionAction.OnOverlayPermissionGranted)
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .safeContentPadding(), contentAlignment = Alignment.Center){
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(0.8f)
        ) {
            PermissionItem(
                heading = "Overlay",
                content = "Overlay Permission Required to Block Screen Usage",
                checked = state.isOverlayGranted,
                onCheckClicked = {
                    onAction(PermissionAction.OnOverlayAction)
                }
            )
            PermissionItem(
                heading = "Storage",
                content = "Storage Permission Required To Wipe Gallery On Request",
                checked = state.isStorageGranted,
                onCheckClicked = {
                    onAction(PermissionAction.OnStorageAction)
                }
            )
            PermissionItem(
                heading = "Other Permissions",
                content = "Camera , Location , Phone , Sms",
                checked = state.isOtherGranted,
                onCheckClicked = {
                    Log.d("KHAN","ON OTHER CLICKED")
                    onAction(PermissionAction.OnOtherAction)
                }
            )
            Button(
                onClick = {
                    onAction(PermissionAction.OnContinueClicked)
                }
            ) {
                Text("Continue")
            }
        }
    }
}