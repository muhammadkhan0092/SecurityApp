package com.example.securityapp.modules.controller.presentation

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
import com.example.securityapp.modules.controller.domain.ControllerDomain
import com.example.securityapp.modules.controller.presentation.components.ControlledDevicesComponent
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun ControllerBarcodeScreen(
    onBarcodeScanned: (String) -> Unit,
    state : List<ControllerDomain>
) {
    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract()
    ) { result ->
        result.contents?.let { barcode ->
            onBarcodeScanned(barcode)
        }
    }
    Box(modifier = Modifier.fillMaxSize().safeContentPadding()){
        Column {
            Box(modifier = Modifier.fillMaxWidth().weight(1f)){
                ControlledDevicesComponent(state)
            }
            Button(
                onClick = {
                    val options = ScanOptions().apply {
                        setDesiredBarcodeFormats(ScanOptions.CODE_128)
                        setPrompt("Scan barcode")
                        setBeepEnabled(true)
                        setOrientationLocked(true)
                    }
                    scanLauncher.launch(options)
                    onBarcodeScanned("")
                }
            ) {
                Text("Scan Barcode")
            }
        }
    }
}