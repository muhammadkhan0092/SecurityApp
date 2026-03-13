package com.example.securityapp.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.securityapp.modules.controlled.presentation.vm.ControlledBarcodeVm
import com.example.securityapp.modules.controlled.presentation.screen.ControlledTabs
import com.example.securityapp.modules.controller.presentation.screens.ControllerBarcodeScreen
import com.example.securityapp.modules.controller.presentation.vm.ControllerCommonVm
import com.example.securityapp.modules.controller.presentation.screens.ControllerTabScreen
import com.example.securityapp.modules.controller.presentation.vm.ControllerActionsVm
import com.example.securityapp.modules.controller.presentation.vm.ControllerHomeVm
import com.example.securityapp.modules.intro.presentation.composables.GateScreen
import com.example.securityapp.modules.intro.presentation.composables.LoginScreenRoot
import com.example.securityapp.modules.packages.PackagesScreenRoot
import com.example.securityapp.modules.intro.presentation.composables.UserTypeScreenRoot
import com.example.securityapp.permissions.PermissionScreenRoot

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.RouteGate
        ) {
            composable<Route.RouteGate> {
                GateScreen(navController)
            }
            composable<Route.Permissions> {
                PermissionScreenRoot(
                   navController
                )
            }
            navigation<Route.IntroGraph>(startDestination = Route.UserType) {
                composable<Route.UserType> { entry ->
                    UserTypeScreenRoot(
                        navController,
                        entry
                    )
                }
                composable<Route.Packages> {
                    PackagesScreenRoot(navController)
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
                    LoginScreenRoot(
                        navController,
                        entry
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
                        vm.events,
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