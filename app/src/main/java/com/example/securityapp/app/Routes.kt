package com.example.securityapp.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object IntroGraph: Route
    @Serializable
    data object UserType: Route
    @Serializable
    data object Login: Route
}