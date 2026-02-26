package com.example.securityapp.modules.controller.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.securityapp.modules.controller.domain.ControllerDomain

@Composable
fun ControlledDevicesComponent(data : List<ControllerDomain>){
    LazyColumn(
    ) {
        items(data){
            ControlledDevicesItem(email = it.email)
        }
    }
}