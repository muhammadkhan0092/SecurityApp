package com.example.securityapp.modules.controlled.presentation

import android.graphics.Bitmap

data class ControlledState(
    val bitmap : Bitmap? = null,
    val selectedIndex : Int = 0
)
