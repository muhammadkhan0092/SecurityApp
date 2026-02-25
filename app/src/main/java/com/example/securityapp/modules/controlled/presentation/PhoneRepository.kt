package com.example.securityapp.modules.controlled.presentation

interface PhoneRepository {

    fun getSimNumbers() : List<String>
    fun isAirplaneModeOn(): Boolean
}