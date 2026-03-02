package com.example.securityapp.modules.controlled.presentation.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.securityapp.modules.controlled.domain.ControlledDomain
import com.example.securityapp.modules.controlled.presentation.components.ControllerListItem
import com.example.securityapp.modules.controlled.presentation.models.ControlledAction

@Composable
fun ControllerListComposable(
    controllers : List<ControlledDomain>,
    onAction : (ControlledAction)-> Unit
) {
    when(controllers.isEmpty()){
        true -> {
            Text("No Devices")
        }
        false -> {
            LazyColumn(modifier = Modifier.fillMaxWidth(0.8f)){
                items(controllers){data->
                    ControllerListItem(
                        data,
                        onAction = onAction
                    )
                }
            }
        }
    }
}