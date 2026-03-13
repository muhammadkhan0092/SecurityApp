package com.example.securityapp.modules.controller.presentation.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.securityapp.modules.controller.presentation.components.ControlledDevicesItem

@Composable
fun ControlledDevicesComponent(data: List<String>, onItemClicked: (String) -> Unit){
    LazyColumn(
    ) {
        items(data){
            ControlledDevicesItem(email = it, onItemClick = onItemClicked)
        }
    }
}