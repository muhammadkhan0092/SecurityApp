package com.example.securityapp.core.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonComposable(
    text : String,
    onClick : ()-> Unit
){
    Button(
        onClick = onClick,
        modifier = Modifier.padding(horizontal = 30.dp, vertical = 15.dp)
    ) {
        Text(text)
    }
}