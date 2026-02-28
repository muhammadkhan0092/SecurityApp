package com.example.securityapp.core.domain

import com.example.securityapp.core.data.DataStoreRepositoryImplementation
import com.example.securityapp.datastore.AppSettings
import com.example.securityapp.modules.controlled.domain.usecase.HandleControlledMessageIntent
import com.example.securityapp.modules.controller.domain.usecase.HandleControllerMessageIntent
import javax.inject.Inject

class HandleMessageIntent @Inject constructor(
    private val dataStoreRepositoryImplementation: DataStoreRepositoryImplementation,
    private val handleControlledMessageIntent: HandleControlledMessageIntent,
    private val handleControllerMessageIntent: HandleControllerMessageIntent
) {
    suspend operator fun invoke(sender: String, message: String) {
        when (dataStoreRepositoryImplementation.getUserType()) {
            AppSettings.UserType.not_set -> Unit
            AppSettings.UserType.controller -> handleControllerMessageIntent(sender, message)
            AppSettings.UserType.controlled -> handleControlledMessageIntent(sender, message)
            AppSettings.UserType.UNRECOGNIZED -> Unit
        }
    }
}