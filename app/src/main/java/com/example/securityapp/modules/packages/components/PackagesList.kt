package com.example.securityapp.modules.packages.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.securityapp.modules.packages.PackagesAction

@Composable
 fun PackagesList(list : List<String>,selectedList: List<String>,onClick:(PackagesAction)-> Unit,modifier: Modifier){
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
    ){
        items(list) {
            PackagesComponent(
                name = it,
                selected = it in selectedList,
                onClick = onClick
            )
        }
    }
}