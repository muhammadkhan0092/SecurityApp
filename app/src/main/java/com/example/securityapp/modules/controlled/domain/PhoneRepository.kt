package com.example.securityapp.modules.controlled.domain

interface PhoneRepository {

    fun getSimNumbers() : List<String>
    fun isAirplaneModeOn(): Boolean
}