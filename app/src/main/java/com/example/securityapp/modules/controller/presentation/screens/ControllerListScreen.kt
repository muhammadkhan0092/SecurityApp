package com.example.securityapp.modules.controller.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.securityapp.modules.controller.presentation.components.ControllerListItem

@Composable
fun ControllerListScreen(data: List<String>, onItemClicked: (String) -> Unit){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(data){
            ControllerListItem(email = it, onItemClick = onItemClicked)
        }
    }
}