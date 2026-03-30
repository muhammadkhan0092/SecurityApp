package com.example.securityapp.modules.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.securityapp.app.Route
import com.example.securityapp.core.presentation.ButtonComposable
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingsScreenRoot(navController: NavController){
    val vm = hiltViewModel<SettingsVm>()
    val state = vm.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        vm.events.collectLatest {
            when(it){
                SettingsEvent.NavigateToIntro -> {
                    navController.navigate(Route.IntroGraph){
                        popUpTo(0)
                    }
                }
            }
        }
    }
    SettingsScreen(
        state.value,
        {
            vm.onAction(it)
        }
    )
}

@Composable
fun SettingsScreen(state: SettingsState, onAction: (SettingsAction) -> Unit) {
    Column(
        modifier = Modifier.safeContentPadding().fillMaxSize()
    ) {
        Text(
            "Settings",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        ButtonComposable("Logout") {
            onAction(SettingsAction.OnLogoutClicked)
        }
        if(state.isLoading) CircularProgressIndicator()
    }
}