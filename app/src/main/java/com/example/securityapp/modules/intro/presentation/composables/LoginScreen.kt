package com.example.securityapp.modules.intro.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.securityapp.modules.intro.presentation.models.LoginAction
import com.example.securityapp.modules.intro.presentation.models.LoginState

@Composable
fun LoginScreen(
    state: LoginState,
    onAction : (LoginAction)-> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        TextField(
            placeholder = {
            Text("Email")
        },
            value = state.email,
            onValueChange = {
                onAction(LoginAction.OnEmailChanged(it))
            }
        )
        TextField(
            placeholder = {
                Text("Password")
            },
            value = state.password,
            onValueChange = {
                onAction(LoginAction.OnPasswordChanged(it))
            }
        )
        Button(onClick = {
            onAction(LoginAction.OnLoginClicked(state.email,state.password))
        }){
            Text("Login")
        }
    }
}