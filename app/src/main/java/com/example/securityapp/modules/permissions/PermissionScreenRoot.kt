package com.example.securityapp.modules.permissions

import android.app.Activity
import android.app.role.RoleManager
import android.app.role.RoleManager.ROLE_SMS
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.securityapp.app.Route
import com.example.securityapp.ui.theme.Purple40
import kotlinx.coroutines.flow.collectLatest


@Composable
fun PermissionScreenRoot(
    navController : NavController
) {
    val vm = hiltViewModel<PermissionVm>()
    val state = vm.state.collectAsStateWithLifecycle()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val allGranted = result.values.all { it }
        if (allGranted) {
            Log.d("Permission", "All runtime permissions granted")
            vm.onAction(PermissionAction.OnOtherPermissionGranted)
        } else {
            Log.d("Permission", "Some permissions denied")
        }
    }
    val backgroundLocationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val allGranted = result.values.all { it }
        if (allGranted) {
            Log.d("Permission", "All runtime permissions granted")
            vm.onAction(PermissionAction.OnBackgroundPermissionGranted)
        } else {
            Log.d("Permission", "Some permissions denied")
        }
    }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val messageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        vm.onAction(PermissionAction.DefaultAppResult(vm.isDefaultAppSet()))
    }
    LaunchedEffect(Unit){
        vm.events.collectLatest {
            when(it){
                PermissionEvent.RequestOtherPermission -> {
                    Log.d("KHAN","REQUESTING RUNTIME PERMISSIONS")
                    launcher.launch(RuntimePermissions.permissions)
                }
                PermissionEvent.RequestOverlayPermission ->  vm.requestOverlayPermission()
                PermissionEvent.RequestStoragePermission -> vm.requestStoragePermission()
                PermissionEvent.NavigateToIntro -> navController.navigate(Route.IntroGraph)
                is PermissionEvent.Toast -> Toast.makeText(context, it.str, Toast.LENGTH_SHORT).show()
                PermissionEvent.RequestMessageDefault -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        val roleManager = context.getSystemService(RoleManager::class.java)
                        messageLauncher.launch(
                            roleManager.createRequestRoleIntent(ROLE_SMS)
                        )
                    } else {
                        val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT).apply {
                            putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, context.packageName)
                        }
                        messageLauncher.launch(intent)
                    }
                }

                PermissionEvent.RequestBackgroundLocationPermission -> backgroundLocationLauncher.launch(
                    RuntimePermissions.backgroundLocation)
            }
        }
    }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (vm.hasOverlayPermission()) {
                    vm.onAction(PermissionAction.OnOverlayPermissionGranted)
                }
                if (vm.hasStoragePermission()) {
                    vm.onAction(PermissionAction.OnStoragePermissionGranted)
                }
                if (vm.isDefaultAppSet()) {
                    vm.onAction(PermissionAction.DefaultAppResult(true))
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    PermissionScreen(
        state.value,
        onAction = {
        vm.onAction(it)
        }
    )
}
@Composable
fun PermissionScreen(state: PermissionState,onAction: (PermissionAction) -> Unit){
    Box(modifier = Modifier
        .fillMaxSize()
        .safeContentPadding(), contentAlignment = Alignment.Center){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopStart)
        ){
            Text(
                "Permissions",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "These Permissions Are required for functioning of our App",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
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
                        onAction(PermissionAction.OnOtherAction)
                    }
                )
                PermissionItem(
                    heading = "Background Location",
                    content = "Background Location is required to send location when app is in background",
                    checked = state.isBackgroundPermissionGranted,
                    onCheckClicked = {
                        onAction(PermissionAction.OnBackGroundAction)
                    }
                )
                PermissionItem(
                    heading = "Default Message App",
                    content = "App needs to be set as default messaging app to function",
                    checked = state.isDefaultAppSet,
                    onCheckClicked = {
                        onAction(PermissionAction.OnMessageDefaultClicked)
                    }
                )
                Spacer(modifier = Modifier.fillMaxWidth().weight(1f))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple40.copy(alpha = 0.6f),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(5.dp),
                    onClick = {
                        onAction(PermissionAction.OnContinueClicked)
                    }
                ) {
                    Text(
                        "Continue",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}