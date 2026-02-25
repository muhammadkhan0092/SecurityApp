package com.example.securityapp.modules.controlled.presentation

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
@Composable
fun ControlleDBarcodeScreen(
    state: Bitmap?
){
    when(state){
        null-> CircularProgressIndicator()
        else -> {
            val imageBitmap = state.asImageBitmap()
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Log.d("KHAN","IN CONTROLLED BARCODE SCREEN")
                Image(
                    bitmap = imageBitmap,
                    contentDescription = "Device Barcode",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}