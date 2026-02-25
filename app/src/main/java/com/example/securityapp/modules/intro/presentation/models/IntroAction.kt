package com.example.securityapp.modules.intro.presentation.models

sealed interface IntroAction {
    data object OnBeControlled : IntroAction
    data object OnControl : IntroAction
}