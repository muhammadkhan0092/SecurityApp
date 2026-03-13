package com.example.securityapp.modules.controller.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ControlledDevicesItem(
    modifier: Modifier = Modifier,
    email: String,
    onItemClick : (String) -> Unit
) {
    Text(
        email,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().clickable{
            onItemClick(email)
        }
    )
}