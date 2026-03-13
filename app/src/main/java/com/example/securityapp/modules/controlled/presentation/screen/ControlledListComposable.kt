package com.example.securityapp.modules.controlled.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text("No Devices")
            }
        }
        false -> {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ){
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