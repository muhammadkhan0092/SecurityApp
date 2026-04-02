package com.example.securityapp.core.presentation

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
import com.example.securityapp.ui.theme.Purple40

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 45.dp,
    backgroundColor: Color = Color.White,
    cornerRadius: Dp = 5.dp,
    hint : String = "Package Name"
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
                    hint,
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
            .border(1.dp, Purple40, RoundedCornerShape(cornerRadius))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    )
}