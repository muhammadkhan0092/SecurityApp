package com.example.securityapp.modules.messages

import android.util.Log
import com.example.securityapp.core.data.repository.DataStoreRepository
import com.example.securityapp.datastore.AppSettings
import com.example.securityapp.modules.controlled.domain.usecase.HandleControlledMessageIntent
import com.example.securityapp.modules.controller.domain.usecase.HandleControllerMessageIntent
import javax.inject.Inject


class HandleMessageIntent @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val handleControlledMessageIntent: HandleControlledMessageIntent,
    private val handleControllerMessageIntent: HandleControllerMessageIntent,
) {
    suspend operator fun invoke(sender: String, message: String) {
        when (dataStoreRepository.getUserType()) {
            AppSettings.UserType.not_set -> {
                Log.d("KHAN", "USER TYPE NOT SET")
            }

            AppSettings.UserType.controller -> {
                Log.d("KHAN", "MESSAGE HANDLE CONTROLLER")
                handleControllerMessageIntent(sender, message)
            }

            AppSettings.UserType.controlled -> {
                Log.d("KHAN", "MESSAGE HANDLE CONTROLLED")
                handleControlledMessageIntent(sender, message)
            }

            AppSettings.UserType.UNRECOGNIZED -> {
                Log.d("KHAN", "USER TYPE UNRECOGNIZED")
            }
        }
    }
}