package com.example.securityapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.securityapp.app.App
import com.example.securityapp.modules.overlay.domain.OverlayRepository
import com.example.securityapp.ui.theme.SecurityAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class
MainActivity : ComponentActivity() {
    @Inject
    lateinit var overlayRepository: OverlayRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SecurityAppTheme {
                App()
               // overlayRepository.startOverlayService()
            }
        }
    }
}