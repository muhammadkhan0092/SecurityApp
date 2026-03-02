package com.example.securityapp.permissions
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PermissionItem(
    heading : String,
    content : String,
    checked : Boolean,
    onCheckClicked : ()-> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(heading, fontWeight = FontWeight.Bold)
            Text("")

        }
        Switch(
            checked = checked,
            onCheckedChange = {newValue ->
                onCheckClicked()
            }
        )
    }
}