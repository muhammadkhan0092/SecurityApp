package com.example.securityapp.modules.intro

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun UserTypeScreen(){
    Column(modifier = Modifier.fillMaxSize()) {
        Text("I want to")
        Button(onClick = {}) {
            Text("Be Controlled")
        }
        Button(onClick = {}) {
            Text("Controll")
        }
    }
}