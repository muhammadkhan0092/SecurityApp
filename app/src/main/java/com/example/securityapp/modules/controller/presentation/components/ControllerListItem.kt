package com.example.securityapp.modules.controller.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.securityapp.ui.theme.Purple40

@Composable
fun ControllerListItem(
    modifier: Modifier = Modifier,
    email: String,
    onItemClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Purple40.copy(alpha = 0.6f),
                shape = RoundedCornerShape(5.dp)
            )
            .clickable {
                onItemClick(email)
            }
            .padding(vertical = 10.dp, horizontal = 10.dp)
    ){
        Text(
            email,
            color = Color.Black
        )
    }
}