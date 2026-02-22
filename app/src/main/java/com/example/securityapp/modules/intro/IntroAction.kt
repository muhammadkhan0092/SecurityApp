package com.example.securityapp.modules.intro

sealed interface IntroAction {
    data object OnBeControlled : IntroAction
    data object OnControl : IntroAction
}