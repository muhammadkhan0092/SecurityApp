package com.example.securityapp.modules.controller.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.securityapp.core.presentation.ButtonComposable
import com.example.securityapp.modules.controller.presentation.models.ControllerTabAction

@Composable
fun ControllerActionsScreen(
    number: String,
    onAction: (ControllerTabAction) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxHeight().fillMaxWidth(0.8f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonComposable("Wipe Data") {
                onAction(ControllerTabAction.OnFactoryReset(number))
            }
            ButtonComposable(text = "Clear Gallery") {
                onAction(ControllerTabAction.OnWipeGallery(number))
            }
            ButtonComposable(text = "Fetch Location") {
                onAction(ControllerTabAction.OnLocationFetch(number))
            }
            ButtonComposable(text = "Block Apps") {
                onAction(ControllerTabAction.OnBlockApps(number))
            }
            ButtonComposable(text = "Uninstall Apps") {
                onAction(ControllerTabAction.OnUninstallClicked(number))
            }
        }
    }
}