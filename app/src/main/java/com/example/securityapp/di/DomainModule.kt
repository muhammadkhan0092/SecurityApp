package com.example.securityapp.di

import com.example.securityapp.modules.controlled.PhoneRepository
import com.example.securityapp.modules.controlled.TelephoneRepository
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
}