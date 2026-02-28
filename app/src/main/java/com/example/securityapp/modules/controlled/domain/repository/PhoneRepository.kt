package com.example.securityapp.modules.controlled.domain.repository

interface PhoneRepository {

    fun getSimNumbers() : List<String>
    fun isAirplaneModeOn(): Boolean
}