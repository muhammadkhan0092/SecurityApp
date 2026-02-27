package com.example.securityapp.modules.controller.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.securityapp.core.domain.MessageTypeFromControlled
import com.example.securityapp.modules.controller.domain.ControllerMessagesDomain


@Composable
fun ControllerMessagesItem(
    data : ControllerMessagesDomain
){
    val color = when(data.type){
        MessageTypeFromControlled.NORMAL -> Color.Black
        MessageTypeFromControlled.ERROR -> Color.Red
    }
    Text(
        text = data.message,
        color = color,
        maxLines = 3
    )
}