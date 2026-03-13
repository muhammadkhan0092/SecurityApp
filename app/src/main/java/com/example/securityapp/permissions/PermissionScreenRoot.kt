package com.example.securityapp.permissions

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit){
        vm.events.collectLatest {
            when(it){
                PermissionEvent.RequestOtherPermission -> launcher.launch(RuntimePermissions.permissions)
                PermissionEvent.RequestOverlayPermission ->  vm.requestOverlayPermission()
                PermissionEvent.RequestStoragePermission -> vm.requestStoragePermission()
                PermissionEvent.NavigateToIntro -> navController.navigate(Route.IntroGraph)
                is PermissionEvent.Toast -> Toast.makeText(context, it.str, Toast.LENGTH_SHORT).show()
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
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        if(vm.hasStoragePermission()){
            vm.onAction(PermissionAction.OnStoragePermissionGranted)
        }
        if(vm.hasOverlayPermission()){
            vm.onAction(PermissionAction.OnOverlayPermissionGranted)
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
                Spacer(modifier = Modifier.fillMaxWidth().weight(1f))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .background(color = Purple40, shape = RoundedCornerShape(10.dp))
                    ,
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