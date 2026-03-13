package com.example.securityapp.modules.intro.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.securityapp.modules.intro.presentation.models.LoginAction
import com.example.securityapp.modules.intro.presentation.models.LoginState
import com.example.securityapp.modules.packages.components.CustomTextField
import com.example.securityapp.modules.packages.components.Radio
import com.example.securityapp.ui.theme.Purple40

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().safeContentPadding(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                "Login",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Purple40
            )
            CustomTextField(
                value = state.email,
                onValueChange = {
                    onAction(LoginAction.OnEmailChanged(it))
                },
                hint = "Email"
            )
            CustomTextField(
                value = state.password,
                onValueChange = {
                    onAction(LoginAction.OnPasswordChanged(it))
                },
                hint = "Password"
            )
            state.numbers?.let {
                if (it.isEmpty()) {
                    CustomTextField(
                        value = state.selectedNumber,
                        onValueChange = {
                            onAction(LoginAction.OnNumberChanged(it))
                        },
                        hint = "Number"
                    )
                } else {
                    it.forEach { current ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onAction(LoginAction.OnNumberClick(current))
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Radio(
                                current == state.selectedNumber
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(current)
                        }
                    }
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth().background(Purple40, RoundedCornerShape(10.dp)),
                onClick = {
                    onAction(
                        LoginAction.OnLoginClicked(
                            state.email,
                            state.password,
                            state.selectedNumber
                        )
                    )
                }
            ) {
                Text("Login")
            }
        }
    }
}