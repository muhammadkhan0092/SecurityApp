package com.example.securityapp.modules.packages.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 45.dp,
    backgroundColor: Color = Color.White,
    cornerRadius: Dp = 12.dp
) {

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = Color.Black
        ),
        decorationBox = { innerTextField ->

            if (value.isEmpty()) {
                Text(
                    "Package Name",
                    fontSize = 18.sp,
                    color = Color.LightGray
                )
            }

            innerTextField()
        },
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(backgroundColor, RoundedCornerShape(cornerRadius))
            .border(2.dp, Color.Gray, RoundedCornerShape(cornerRadius))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    )
}