package com.example.securityapp.modules.intro

import android.util.Log

sealed interface LoginEvents {
    data object NavigateToControlledHome : LoginEvents
    data class Toast(val str : String) : LoginEvents
}