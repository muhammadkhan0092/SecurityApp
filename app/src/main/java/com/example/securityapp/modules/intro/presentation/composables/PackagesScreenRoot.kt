package com.example.securityapp.modules.intro.presentation.composables

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.securityapp.app.Route
import com.example.securityapp.modules.intro.presentation.models.PackagesAction
import com.example.securityapp.modules.intro.presentation.models.PackagesEvent
import com.example.securityapp.modules.intro.presentation.models.PackagesState
import com.example.securityapp.modules.intro.presentation.vm.PackagesVm
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PackagesScreenRoot(
    navController: NavController
){
    val vm = hiltViewModel<PackagesVm>()
    val state = vm.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        vm.events.collectLatest {
            when(it){
                PackagesEvent.NavigateToControlledMain -> {
                    navController.navigate(Route.ControlledHomeGraph) {
                        popUpTo(Route.IntroGraph) { inclusive = true }
                        launchSingleTop = true
                    }
                }
                is PackagesEvent.Toast -> Toast.makeText(context, it.str, Toast.LENGTH_SHORT).show()
            }
        }
    }
    PackagesScreen(
        state.value,
        {
            vm.onAction(it)
        }
    )
}
@Composable
private fun PackagesScreen(state: PackagesState,onClick:(PackagesAction)-> Unit) {
    Log.d("KHAN","SELECTED PACKAGES ARE ${state.selectedPackages}")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
    ){
        when(state.allPackages){
            null->{
                CircularProgressIndicator(
                     modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> PackagesList(list = state.allPackages, selectedList = state.selectedPackages, onClick = onClick)
        }
    }
}
@Composable
private fun PackagesList(list : List<String>,selectedList: List<String>,onClick:(PackagesAction)-> Unit){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ){
        items(list){
            PackagesComponent(
                name = it,
                selected = it in selectedList,
                onClick = onClick
            )
        }
    }
}

@Composable fun PackagesComponent(name : String,selected : Boolean,onClick:(PackagesAction)-> Unit){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        RadioButton(
            selected = selected,
            onClick = {
            onClick(PackagesAction.OnPackageClicked(name))
            }
        )
        Text(name)
    }
}