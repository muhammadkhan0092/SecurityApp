package com.example.securityapp.permissions

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun SpecialPermissionButtons() {
    val context = LocalContext.current
    Column {
        Button(
            onClick = {
                if (!SpecialPermissions.hasOverlayPermission(context)) {
                    SpecialPermissions.requestOverlayPermission(context)
                }
            }
        ) {
            Text("Grant Overlay Permission")
        }
        Button(
            onClick = {
                if (!hasManageAllFilesPermission()) {
                    requestManageAllFilesPermission(context)
                }
            }
        ) {
            Text("Grant All Files Access")
        }
    }
}