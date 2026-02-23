package com.example.securityapp.modules.controlled

interface PhoneRepository {

    fun getSimNumbers() : List<String>
}