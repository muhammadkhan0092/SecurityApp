package com.example.securityapp.di

import com.example.securityapp.modules.controlled.domain.PhoneRepository
import com.example.securityapp.modules.controlled.domain.repository.OverlayRepository
import com.example.securityapp.modules.controlled.presentation.TelephoneRepository
import com.example.securityapp.modules.controlled.presentation.service.OverlayControllerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {
    @Singleton
    @Provides
    fun returnPhoneRepo(repo : TelephoneRepository) : PhoneRepository {
       return repo
    }

    @Singleton
    @Provides
    fun providesOverlay(repo : OverlayControllerImpl) : OverlayRepository {
        return repo
    }
}