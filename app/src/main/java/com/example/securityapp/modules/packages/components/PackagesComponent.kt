package com.example.securityapp.modules.packages.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.securityapp.modules.packages.PackagesAction
import com.example.securityapp.ui.theme.Purple40

@Composable fun PackagesComponent(name : String, selected : Boolean, onClick:(PackagesAction)-> Unit){
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp).clickable{
            onClick(PackagesAction.OnPackageClicked(name))
        },
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        Radio(selected = selected)
        Text(
            name, maxLines = 1,
            fontSize = 16.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
fun Radio(selected: Boolean){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.width(20.dp).height(20.dp).background(
            color = Color.White,
            shape = RoundedCornerShape(10.dp)
        )
            .border(1.dp, Color.LightGray)
    ){
        if(selected){
            Box(
                modifier = Modifier.fillMaxSize(0.9f)
                    .background(
                        color =  Purple40
                    )
            )
        }
    }
}