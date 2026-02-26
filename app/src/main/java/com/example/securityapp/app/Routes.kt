package com.example.securityapp.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object IntroGraph: Route
    @Serializable
    data object UserType: Route
    @Serializable
    data object Login: Route
    @Serializable
    data object RouteGate: Route


    @Serializable
    data object ControlledHomeGraph: Route
    @Serializable
    data object ControlledBarcode: Route

    @Serializable
    data object ControllerHomeGraph: Route
    @Serializable
    data object ControllerBarcode: Route
    @Serializable
    data object ControllerActions: Route
}