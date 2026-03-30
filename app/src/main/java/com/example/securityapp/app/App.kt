package com.example.securityapp.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.securityapp.modules.controlled.presentation.screen.ControlledTabsRoot
import com.example.securityapp.modules.controller.presentation.screens.ControllerBarcodeScreenRoot
import com.example.securityapp.modules.controller.presentation.screens.ControllerTabScreenRoot
import com.example.securityapp.modules.intro.presentation.composables.GateScreen
import com.example.securityapp.modules.intro.presentation.composables.LoginScreenRoot
import com.example.securityapp.modules.packages.PackagesScreenRoot
import com.example.securityapp.modules.intro.presentation.composables.UserTypeScreenRoot
import com.example.securityapp.modules.permissions.PermissionScreenRoot
import com.example.securityapp.modules.settings.presentation.SettingsScreenRoot

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
            composable<Route.Settings> {
                SettingsScreenRoot(navController)
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
                    ControlledTabsRoot(navController)
                }
            }
            navigation<Route.ControllerHomeGraph>(startDestination = Route.ControllerBarcode) {
                composable<Route.ControllerBarcode>(
                ) { entry ->
                    ControllerBarcodeScreenRoot(navController,entry)
                }
                composable<Route.ControllerActions>(
                ) { entry ->
                    ControllerTabScreenRoot(
                        navController,
                        entry
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