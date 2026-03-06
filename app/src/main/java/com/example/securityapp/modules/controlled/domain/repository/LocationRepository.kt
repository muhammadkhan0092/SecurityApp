package com.example.securityapp.modules.controlled.domain.repository

import android.location.Location

interface LocationRepository{
    suspend fun getAccurateLocation(): Location?
}