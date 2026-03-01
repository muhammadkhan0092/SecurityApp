package com.example.securityapp.modules.intro.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.securityapp.modules.intro.presentation.models.IntroAction

@Composable
fun UserTypeScreen(
    onAction : (IntroAction)-> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("I want to")
            Button(onClick ={onAction(IntroAction.OnBeControlled)}) {
                Text("Be Controlled")
            }
            Button(
                onClick = {
                    onAction(IntroAction.OnControl)
                }
            ) {
                Text("Controll")
            }
        }
    }
}