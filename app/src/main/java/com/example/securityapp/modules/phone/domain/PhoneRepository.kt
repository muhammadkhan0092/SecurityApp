package com.example.securityapp.modules.phone.domain

interface PhoneRepository {

    fun getSimNumbers() : List<String>
    fun isAirplaneModeOn(): Boolean
}