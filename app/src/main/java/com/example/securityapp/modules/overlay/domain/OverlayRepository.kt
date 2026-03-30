package com.example.securityapp.modules.overlay.domain

interface OverlayRepository {
    fun startOverlayService()
    fun stopOverlayService()
}