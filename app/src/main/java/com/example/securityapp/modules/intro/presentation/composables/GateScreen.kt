package com.example.securityapp.modules.intro.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.securityapp.app.Route
import com.example.securityapp.modules.intro.presentation.models.GateEvents
import com.example.securityapp.modules.intro.presentation.vm.IntroVm

@Composable
fun GateScreen(navController: NavHostController) {
    val vm = hiltViewModel<IntroVm>()
    val state = vm.state.collectAsStateWithLifecycle()
    when(state.value){
        GateEvents.NavigateToController -> navController.navigate(Route.ControllerHomeGraph)
        GateEvents.NavigateToControlled -> navController.navigate(Route.ControlledHomeGraph)
        GateEvents.NavigateToPackages -> navController.navigate(Route.Packages)
        GateEvents.NavigateToPermissions -> navController.navigate(Route.Permissions)
        GateEvents.None -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
    }
}