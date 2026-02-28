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
import com.example.securityapp.modules.intro.presentation.vm.IntroSharedVm
import com.example.securityapp.modules.intro.presentation.vm.LoginCommonVm
import com.example.securityapp.modules.intro.presentation.composables.LoginScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.securityapp.datastore.AppSettings.UserType.*
import com.example.securityapp.modules.controlled.presentation.vm.ControlledBarcodeVm
import com.example.securityapp.modules.controlled.presentation.screen.ControlledTabs
import com.example.securityapp.modules.controller.presentation.screens.ControllerBarcodeScreen
import com.example.securityapp.modules.controller.presentation.vm.ControllerCommonVm
import com.example.securityapp.modules.controller.presentation.screens.ControllerTabScreen
import com.example.securityapp.modules.controller.presentation.vm.ControllerActionsVm
import com.example.securityapp.modules.controller.presentation.vm.ControllerHomeVm
import com.example.securityapp.modules.intro.presentation.composables.GateScreen
import com.example.securityapp.modules.intro.presentation.vm.IntroVm
import com.example.securityapp.modules.intro.presentation.vm.LoginControlledVm
import com.example.securityapp.modules.intro.presentation.vm.LoginControllerVm
import com.example.securityapp.modules.intro.presentation.models.LoginEvents
import com.example.securityapp.modules.intro.presentation.composables.UserTypeScreen
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
                            Log.d("KHAN","ON ACTION USER TYPE IS $userType")
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
            }
            navigation<Route.ControlledHomeGraph>(startDestination = Route.ControlledBarcode) {
                composable<Route.ControlledBarcode>(
                ) { entry ->
                    val vm = hiltViewModel<ControlledBarcodeVm>()
                    val state by vm.state.collectAsStateWithLifecycle()
                    ControlledTabs(
                        state = state,
                        onAction = {
                            vm.onAction(it)
                        }
                    )
                }
            }
            navigation<Route.ControllerHomeGraph>(startDestination = Route.ControllerBarcode) {
                composable<Route.ControllerBarcode>(
                ) { entry ->
                    val commonVm = entry.sharedHiltViewModel<ControllerCommonVm>(navController)
                    val vm = hiltViewModel<ControllerHomeVm>()
                    val state=  commonVm.state.collectAsStateWithLifecycle(null)
                    val homeState = vm.state.collectAsStateWithLifecycle()
                    when(val value =state.value){
                        null-> Unit
                        else -> vm.onStateChanged(value)
                    }
                    ControllerBarcodeScreen(
                        onBarcodeScanned = {
                            vm.connect(it,state.value)
                        },
                        onItemClicked ={it: String->
                            commonVm.onItemClicked(it)
                            navController.navigate(Route.ControllerActions)
                        },
                        state = homeState.value
                    )
                }
                composable<Route.ControllerActions>(
                ) { entry ->
                    val commonControllerCommonVm = entry.sharedHiltViewModel<ControllerCommonVm>(navController)
                    val commonState=  commonControllerCommonVm.state.collectAsStateWithLifecycle()
                    val vm = hiltViewModel<ControllerActionsVm>()
                    val state = vm.state.collectAsStateWithLifecycle()
                    when(commonState.value){
                        null-> Unit
                        else -> vm.onNumberReceived(commonControllerCommonVm.selectedController)
                    }
                    ControllerTabScreen(
                        state = state.value,
                        {
                            vm.onAction(it)
                        }
                    )
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