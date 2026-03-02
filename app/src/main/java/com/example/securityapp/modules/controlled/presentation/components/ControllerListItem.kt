package com.example.securityapp.modules.controlled.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.securityapp.modules.controlled.domain.ControlledDomain
import com.example.securityapp.modules.controlled.presentation.models.ControlledAction

@Composable
fun ControllerListItem(
    data: ControlledDomain,
    onAction :(ControlledAction)-> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = data.email)
        IconButton(
            onClick = {
                onAction(ControlledAction.OnDeleteClicked(data.email))
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "",
            )
        }
    }
}