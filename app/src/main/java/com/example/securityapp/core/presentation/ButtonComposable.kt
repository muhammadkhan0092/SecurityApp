package com.example.securityapp.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.securityapp.ui.theme.Purple40

@Composable
fun ButtonComposable(
    text : String,
    onClick : ()-> Unit
){
    Button(
        onClick = onClick,
        modifier = Modifier
            .background(Purple40, RoundedCornerShape(10.dp))
            .fillMaxWidth(0.8f)
    ) {
        Text(text)
    }
}