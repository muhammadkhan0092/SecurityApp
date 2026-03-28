package com.example.securityapp.core.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.securityapp.ui.theme.Purple40

@Composable
fun ButtonComposable(
    text : String,
    onClick : ()-> Unit
){
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Purple40.copy(alpha = 0.6f),
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(5.dp),
        onClick =onClick
    ) {
        Text(text,fontFamily = FontFamily.Cursive)
    }
}