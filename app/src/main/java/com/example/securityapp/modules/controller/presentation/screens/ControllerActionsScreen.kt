package com.example.securityapp.modules.controller.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.securityapp.core.presentation.ButtonComposable
import com.example.securityapp.modules.controller.presentation.models.ControllerActionsState

@Composable
fun ControllerActionsScreen(
     state : ControllerActionsState
){
    Box(modifier = Modifier.fillMaxSize().safeContentPadding()){
        when(state.isLoading){
            true -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            false -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ){
                    NumbersComposable(numbers = state.numbers)
                    ButtonComposable("Wipe Data") {

                    }
                    ButtonComposable(text = "Clear Gallery") {

                    }
                    ButtonComposable(text = "Fetch Location") {

                    }
                    ButtonComposable(text = "Block Apps") {

                    }
                }
            }
        }
    }
}

@Composable
fun NumbersComposable(modifier: Modifier = Modifier,numbers : List<String>){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        Text("Ph No : ")
        Column {
            numbers.forEach {
                Text(it)
            }
        }
    }
}