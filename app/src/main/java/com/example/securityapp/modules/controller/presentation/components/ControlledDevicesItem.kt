package com.example.securityapp.modules.controller.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ControlledDevicesItem(modifier: Modifier = Modifier, email: String) {
    Text(
        email,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}