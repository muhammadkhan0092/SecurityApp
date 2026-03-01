package com.example.securityapp.modules.intro.presentation.composables

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.securityapp.app.Route
import com.example.securityapp.datastore.AppSettings

@Composable
fun GateScreen(navController: NavHostController, state:AppSettings.UserType?) {
    Log.d("KHAN","STATE RECEIVED IS $state")
    when(state){
        AppSettings.UserType.controller -> navController.navigate(Route.ControllerHomeGraph)
        AppSettings.UserType.controlled -> navController.navigate(Route.ControlledHomeGraph)
        AppSettings.UserType.UNRECOGNIZED -> Unit
        null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }

        AppSettings.UserType.not_set -> navController.navigate(Route.Permissions)
    }
}