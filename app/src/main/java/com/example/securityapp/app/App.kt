package com.example.securityapp.app

import android.util.Log
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.securityapp.modules.intro.IntroSharedVm
import com.example.securityapp.modules.intro.LoginCommonVm
import com.example.securityapp.modules.intro.LoginScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.securityapp.datastore.AppSettings.UserType.*
import com.example.securityapp.modules.controlled.ControllerBarcodeScreen
import com.example.securityapp.modules.intro.GateScreen
import com.example.securityapp.modules.intro.IntroVm
import com.example.securityapp.modules.intro.LoginControlledVm
import com.example.securityapp.modules.intro.LoginControllerVm
import com.example.securityapp.modules.intro.LoginEvents
import com.example.securityapp.modules.intro.UserTypeScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.RouteGate
        ) {
            composable<Route.RouteGate> {
                val vm = hiltViewModel<IntroVm>()
                val state = vm.userType.collectAsStateWithLifecycle()
                GateScreen(navController, state.value)
            }
            navigation<Route.IntroGraph>(startDestination = Route.UserType) {
                Log.d("KHAN", "IN INTRO GRAPH")
                composable<Route.UserType>(
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = { slideInHorizontally() }
                ) { entry ->
                    val sharedVm = entry.sharedHiltViewModel<IntroSharedVm>(navController)
                    UserTypeScreen(
                        onAction = {
                            sharedVm.onAction(it)
                            navController.navigate(Route.Login)
                        }
                    )
                }
            }
            composable<Route.Login>(
                enterTransition = {
                    slideInHorizontally { offset ->
                        offset
                    }
                },
                exitTransition = {
                    slideOutHorizontally { offset ->
                        offset
                    }
                }
            ) { entry ->
                val sharedVm = entry.sharedHiltViewModel<IntroSharedVm>(navController)
                val userType = sharedVm.userType
                val loginCommonVm: LoginCommonVm = hiltViewModel()
                val loginControllerVm: LoginControllerVm = hiltViewModel()
                val loginControlledByVm: LoginControlledVm = hiltViewModel()
                val loginState by loginCommonVm.state.collectAsStateWithLifecycle()
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
                    loginState,
                    {
                        loginCommonVm.onAction(it)
                        when (userType) {
                            controller -> loginControllerVm.onAction(it)
                            controlled -> loginControlledByVm.onAction(it)
                            UNRECOGNIZED -> Unit
                            not_set -> Unit
                        }
                    }
                )
            }
            navigation<Route.ControlledHomeGraph>(startDestination = Route.ControlledBarcode) {
                Log.d("KHAN", "IN CONTROLLED HOME GRAPH")
                composable<Route.ControlledBarcode>(
                ) { entry ->
                    ControllerBarcodeScreen()
                }
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedHiltViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}