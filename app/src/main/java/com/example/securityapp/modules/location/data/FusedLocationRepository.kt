package com.example.securityapp.modules.location.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.example.securityapp.modules.location.domain.LocationRepository
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class FusedLocationRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationRepository {
    @SuppressLint("MissingPermission")
    override suspend fun getAccurateLocation(): Location? =
        try {
            suspendCancellableCoroutine { cont ->
                val client = LocationServices.getFusedLocationProviderClient(context)
                val cancellationTokenSource = CancellationTokenSource()

                client.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    cancellationTokenSource.token
                ).addOnSuccessListener { location ->
                    cont.resume(location) {}
                }.addOnFailureListener {
                    cont.resume(null) {}
                }

                cont.invokeOnCancellation {
                    cancellationTokenSource.cancel()
                }
            }
        } catch (e: Exception) {
            null
        }
}