package com.example.securityapp.modules.controller

import androidx.compose.runtime.Composable

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun ControllerBarcodeScreen(
    onBarcodeScanned: (String) -> Unit
) {
    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract()
    ) { result ->
        result.contents?.let { barcode ->
            onBarcodeScanned(barcode)
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Button(
            onClick = {
                val options = ScanOptions().apply {
                    setDesiredBarcodeFormats(ScanOptions.CODE_128)
                    setPrompt("Scan barcode")
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