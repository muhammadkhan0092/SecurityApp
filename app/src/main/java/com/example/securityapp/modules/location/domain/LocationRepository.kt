package com.example.securityapp.modules.location.domain

import android.location.Location

interface LocationRepository{
    suspend fun getAccurateLocation(): Location?
}