package com.example.securityapp.modules.messages.domain.usecase

import android.util.Log
import com.example.securityapp.modules.app_settings.data.AppAppSettingsRepoImpl
import com.example.securityapp.datastore.AppSettings
import javax.inject.Inject


class HandleMessageIntent @Inject constructor(
    private val appSettingsRepoImpl: AppAppSettingsRepoImpl,
    private val handleControlledMessageIntent: HandleControlledMessageIntent,
    private val handleControllerMessageIntent: HandleControllerMessageIntent,
) {
    suspend operator fun invoke(sender: String, message: String) {
        when (appSettingsRepoImpl.getUserType()) {
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