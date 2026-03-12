package com.example.securityapp.modules.intro.presentation.models

sealed interface IntroAction {
    data object OnContinueClicked : IntroAction
    data object OnBeControlled : IntroAction
    data object OnControl : IntroAction
}