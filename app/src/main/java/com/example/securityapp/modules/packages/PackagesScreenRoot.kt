package com.example.securityapp.modules.packages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.securityapp.app.Route
import com.example.securityapp.core.presentation.ButtonComposable
import com.example.securityapp.modules.packages.components.CustomTextField
import com.example.securityapp.modules.packages.components.PackagesList
import com.example.securityapp.ui.theme.Purple40
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
                        popUpTo(0)
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
            else -> {
                Column(modifier = Modifier.fillMaxSize()){
                    Text(
                        "Select Apps",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                    Text(
                        "Choose Apps You want to Uninstall",
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CustomTextField(
                        value = state.etValue,
                        onValueChange = {
                            Log.d("KHAN","ON VALUE CHANGED $it")
                            onClick(PackagesAction.OnTextChanged(it))
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "Selected : ${state.selectedPackages.size}",
                        color = Purple40,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    PackagesList(list = state.allPackages, selectedList = state.selectedPackages, onClick = onClick, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.height(10.dp))
                   ButtonComposable(text = "Continue") {
                       onClick(PackagesAction.OnNextClicked)
                   }
                }
            }
        }
    }
}
