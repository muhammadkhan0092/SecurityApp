package com.example.securityapp.modules.intro.presentation.composables

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.securityapp.app.Route
import com.example.securityapp.app.sharedHiltViewModel
import com.example.securityapp.datastore.AppSettings.UserType.UNRECOGNIZED
import com.example.securityapp.datastore.AppSettings.UserType.controlled
import com.example.securityapp.datastore.AppSettings.UserType.controller
import com.example.securityapp.datastore.AppSettings.UserType.not_set
import com.example.securityapp.modules.intro.presentation.models.LoginAction
import com.example.securityapp.modules.intro.presentation.models.LoginEvents
import com.example.securityapp.modules.intro.presentation.models.LoginState
import com.example.securityapp.modules.intro.presentation.vm.IntroSharedVm
import com.example.securityapp.modules.intro.presentation.vm.LoginCommonVm
import com.example.securityapp.modules.intro.presentation.vm.LoginControlledVm
import com.example.securityapp.modules.intro.presentation.vm.LoginControllerVm
import com.example.securityapp.modules.packages.components.CustomTextField
import com.example.securityapp.modules.packages.components.Radio
import com.example.securityapp.ui.theme.Purple40
import kotlinx.coroutines.flow.collectLatest


@Composable
fun LoginScreenRoot(navController: NavHostController, entry: NavBackStackEntry) {
    val sharedVm = entry.sharedHiltViewModel<IntroSharedVm>(navController)
    val userType = sharedVm.userType
    val loginCommonVm: LoginCommonVm = hiltViewModel()
    val loginControllerVm: LoginControllerVm = hiltViewModel()
    val loginControlledByVm: LoginControlledVm = hiltViewModel()
    val loginState by loginCommonVm.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        loginControlledByVm.events.collectLatest {
            when (it) {
                LoginEvents.NavigateToControlledHome -> navController.navigate(Route.Packages)
                is LoginEvents.Toast -> {
                    Log.d("KHAN", "TOAST ${it.str}")
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        loginControlledByVm.events.collectLatest {
            when (it) {
                LoginEvents.NavigateToControlledHome -> navController.navigate(Route.ControlledHomeGraph) {
                    popUpTo(Route.IntroGraph) { inclusive = true }
                    launchSingleTop = true
                }

                is LoginEvents.Toast -> {
                    Log.d("KHAN", "TOAST ${it.str}")
                }
            }
        }
    }
    LoginScreen(
        state = loginState
    ) {
        loginCommonVm.onAction(it)
        when (userType) {
            controller -> loginControllerVm.onAction(it)
            controlled -> loginControlledByVm.onAction(it)
            UNRECOGNIZED -> Unit
            not_set -> Unit
        }
    }
}
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