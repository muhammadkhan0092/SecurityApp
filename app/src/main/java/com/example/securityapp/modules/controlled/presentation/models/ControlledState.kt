package com.example.securityapp.modules.controlled.presentation.models

import android.graphics.Bitmap
import com.example.securityapp.modules.controller.domain.MessagesDomain

data class ControlledState(
    val bitmap : Bitmap? = null,
    val selectedIndex : Int = 0,
    val messages : List<MessagesDomain> = emptyList()
)