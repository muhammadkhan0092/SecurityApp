package com.example.securityapp.core.data.repository

sealed interface CommandOwner{
    data class Controlled(val data : String) : CommandOwner
    data class Controller(val data : String) : CommandOwner
}