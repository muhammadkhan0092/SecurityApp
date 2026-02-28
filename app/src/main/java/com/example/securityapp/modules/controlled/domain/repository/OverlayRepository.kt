package com.example.securityapp.modules.controlled.domain.repository

interface OverlayRepository {
    fun startOverlayService()
    fun stopOverlayService()
}