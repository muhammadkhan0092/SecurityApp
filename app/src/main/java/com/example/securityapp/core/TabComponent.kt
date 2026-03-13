package com.example.securityapp.core

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.securityapp.ui.theme.Purple40

@Composable
fun TabComponent(
    isSelected : Boolean,
    onClick :()-> Unit,
    text : String
){
    Tab(
        selected = isSelected,
        onClick = {
            onClick()
        },

        selectedContentColor = Purple40,
        unselectedContentColor = Color.Black
    ) {
        Text(
            text,modifier = Modifier.padding(vertical = 30.dp),
            fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Light
        )
    }
}