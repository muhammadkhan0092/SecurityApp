package com.example.securityapp.modules.controller.presentation.screens

import androidx.compose.runtime.Composable

import androidx.compose.material3.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.securityapp.app.Route
import com.example.securityapp.app.sharedHiltViewModel
import com.example.securityapp.modules.controller.presentation.models.ControllerHomeState
import com.example.securityapp.modules.controller.presentation.vm.ControllerCommonVm
import com.example.securityapp.modules.controller.presentation.vm.ControllerHomeVm
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun ControllerBarcodeScreenRoot(navController: NavHostController, entry: NavBackStackEntry) {
    val commonVm = entry.sharedHiltViewModel<ControllerCommonVm>(navController)
    val vm = hiltViewModel<ControllerHomeVm>()
    val state=  commonVm.state.collectAsStateWithLifecycle(null)
    val homeState = vm.state.collectAsStateWithLifecycle()
    when(val value =state.value){
        null-> Unit
        else -> vm.onStateChanged(value)
    }
    ControllerBarcodeScreen(
        onBarcodeScanned = {
            vm.connect(it,state.value)
        },
        state = homeState.value
    ) {
        commonVm.onItemClicked(it)
        navController.navigate(Route.ControllerActions)
    }
}

@Composable
fun ControllerBarcodeScreen(
    onBarcodeScanned: (String) -> Unit,
    state: ControllerHomeState,
    onItemClicked: (String) -> Unit
) {
    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract()
    ) { result ->
        result.contents?.let { barcode ->
            onBarcodeScanned(barcode)
        }
    }
    Box(modifier = Modifier.fillMaxSize().safeContentPadding()){
        when(state.isLoading){
            true -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
            false -> {
                Column {
                    Box(modifier = Modifier.fillMaxWidth().weight(1f)){
                        when(state.isEmpty){
                            true -> Text("No Devices Yet", modifier = Modifier.align(Alignment.Center))
                            false ->  ControlledDevicesComponent(state.controlledEmails,onItemClicked)
                        }
                    }
                    Button(
                        onClick = {
                            val options = ScanOptions().apply {
                                setDesiredBarcodeFormats(listOf(ScanOptions.QR_CODE)) // QR instead of CODE_128
                                setPrompt("Scan QR code")
                                setBeepEnabled(true)
                                setOrientationLocked(true)
                            }
                            scanLauncher.launch(options)
                        }
                    ) {
                        Text("Scan Barcode")
                    }
                }
            }
        }
    }
}