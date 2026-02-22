package com.example.securityapp.app

import android.util.Log
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.securityapp.modules.intro.IntroSharedVm
import com.example.securityapp.modules.intro.LoginCommonVm
import com.example.securityapp.modules.intro.LoginScreen
import com.example.securityapp.modules.intro.UserTypeScreen
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.securityapp.datastore.AppSettings
import com.example.securityapp.modules.intro.LoginControlledVm
import com.example.securityapp.modules.intro.LoginControllerVm
import kotlin.math.log

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.IntroGraph
        ){
            navigation<Route.IntroGraph>(startDestination = Route.UserType){
                composable<Route.UserType>(
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = { slideInHorizontally() }
                ){entry->
                    val sharedVm = entry.sharedHiltViewModel<IntroSharedVm>(navController)
                    UserTypeScreen(
                        onAction = {
                            sharedVm.onAction(it)
                            navController.navigate(Route.Login)
                        }
                    )
                }
                composable<Route.Login>(
                    enterTransition = {slideInHorizontally{offset->
                        offset
                    }},
                    exitTransition = {slideOutHorizontally{offset->
                        offset
                    }}
                ){entry->
                    val sharedVm = entry.sharedHiltViewModel<IntroSharedVm>(navController)
                    val userType = sharedVm.userType
                    val loginCommonVm : LoginCommonVm = hiltViewModel()
                    val loginControllerVm : LoginControllerVm = hiltViewModel()
                    val loginControlledByVm : LoginControlledVm = hiltViewModel()
                    val loginState by loginCommonVm.state.collectAsStateWithLifecycle()
                    LoginScreen(
                        loginState,
                        {
                            loginCommonVm.onAction(it)
                            when(userType){
                                AppSettings.UserType.controller -> loginControllerVm.onAction(it)
                                AppSettings.UserType.controlled -> loginControlledByVm.onAction(it)
                                AppSettings.UserType.UNRECOGNIZED -> TODO()
                            }
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