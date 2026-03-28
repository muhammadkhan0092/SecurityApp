package com.example.securityapp.modules.controlled.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.securityapp.modules.controlled.domain.ControlledDomain
import com.example.securityapp.modules.controlled.presentation.models.ControlledAction
import com.example.securityapp.ui.theme.Purple40

@Composable
fun ControlledListItem(
    data: ControlledDomain,
    onAction :(ControlledAction)-> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(
            color = Purple40.copy(alpha = 0.6f),
            shape = RoundedCornerShape(10.dp)
        )
            .padding(start = 10.dp)
    ) {
        Text(
            text = data.email,
            modifier = Modifier.weight(1f)
        )
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