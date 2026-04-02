package com.example.securityapp.modules.permissions.presentation.ui
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.securityapp.modules.packages.presentation.ui.Radio

@Composable
fun PermissionItem(
    heading : String,
    content : String,
    checked : Boolean,
    onCheckClicked : ()-> Unit
){
    Row(
        modifier = Modifier.clickable{
            onCheckClicked()
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        Radio(selected = checked)
        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(heading, fontWeight = FontWeight.Bold)
            Text(content)

        }
    }
}