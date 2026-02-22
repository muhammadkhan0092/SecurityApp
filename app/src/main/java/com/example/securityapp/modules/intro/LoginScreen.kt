package com.example.securityapp.modules.intro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        TextField(
            placeholder = {
            Text("Email")
        },
            value = "",
            onValueChange = {}
        )
        TextField(
            placeholder = {
                Text("Password")
            },
            value = "",
            onValueChange = {}
        )
        Button(onClick = {}){
            Text("Login")
        }
    }
}