package com.example.securityapp.modules.controller.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

@Composable
fun ControlledDevicesComponent(data: List<String>){
    LazyColumn(
    ) {
        items(data){
            ControlledDevicesItem(email = it)
        }
    }
}