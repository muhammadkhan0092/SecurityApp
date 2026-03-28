package com.example.securityapp.di

import android.os.Message
import com.example.securityapp.core.data.repository.AndroidSmsManagerRepository
import com.example.securityapp.modules.uninstall.AndroidUninstallRepository
import com.example.securityapp.core.data.repository.DataStoreRepository
import com.example.securityapp.core.data.repository.FirebaseConnectionRepository
import com.example.securityapp.core.data.repository.RoomMessagesRepository
import com.example.securityapp.core.data.repository.SecurityLoginRepository
import com.example.securityapp.core.domain.repository.ConnectionRepository
import com.example.securityapp.core.domain.repository.LoginRepository
import com.example.securityapp.modules.messages.MessagesRepository
import com.example.securityapp.core.domain.repository.SettingsRepository
import com.example.securityapp.modules.messages.SmsManagerRepository
import com.example.securityapp.modules.uninstall.UninstallRepository
import com.example.securityapp.modules.controlled.data.repository.AndroidDeviceOwnerRepository
import com.example.securityapp.modules.controlled.data.repository.AndroidGalleryRepository
import com.example.securityapp.modules.controlled.data.repository.FirebaseControlledRepository
import com.example.securityapp.modules.controlled.data.repository.FusedLocationRepository
import com.example.securityapp.modules.controlled.domain.repository.PhoneRepository
import com.example.securityapp.modules.controlled.domain.repository.OverlayRepository
import com.example.securityapp.modules.controlled.data.repository.TelephoneRepository
import com.example.securityapp.modules.controlled.domain.repository.ControlledRepository
import com.example.securityapp.modules.controlled.domain.repository.DeviceOwnerRepository
import com.example.securityapp.modules.controlled.domain.repository.GalleryRepository
import com.example.securityapp.modules.controlled.domain.repository.LocationRepository
import com.example.securityapp.modules.controlled.presentation.service.OverlayControllerImpl
import com.example.securityapp.modules.controller.data.repository.FirebaseControllerRepository
import com.example.securityapp.modules.controller.domain.repository.ControllerRepository
import com.example.securityapp.modules.intro.data.AndroidPackageRepository
import com.example.securityapp.modules.intro.domain.PackageRepository
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


    @Singleton
    @Provides
    fun providesConnectionRepository(repo : FirebaseConnectionRepository) : ConnectionRepository {
        return repo
    }


    @Singleton
    @Provides
    fun providesLoginRepository(repo : SecurityLoginRepository) : LoginRepository {
        return repo
    }


    @Singleton
    @Provides
    fun providesMessageRepository(repo : RoomMessagesRepository) : MessagesRepository {
        return repo
    }


    @Singleton
    @Provides
    fun providesSettingsRepository(repo : DataStoreRepository) : SettingsRepository {
        return repo
    }


    @Singleton
    @Provides
    fun providesSmsManagerRepository(repo : AndroidSmsManagerRepository) : SmsManagerRepository {
        return repo
    }


    @Singleton
    @Provides
    fun providesDeviceOwnerRepository(repo : AndroidDeviceOwnerRepository) : DeviceOwnerRepository {
        return repo
    }



    @Singleton
    @Provides
    fun providesGalleryRepository(repo : AndroidGalleryRepository) : GalleryRepository {
        return repo
    }


    @Singleton
    @Provides
    fun providesControlledRepository(repo : FirebaseControlledRepository) : ControlledRepository {
        return repo
    }



    @Singleton
    @Provides
    fun providesLocationRepository(repo : FusedLocationRepository) : LocationRepository {
        return repo
    }


    @Singleton
    @Provides
    fun providesControllerRepository(repo : FirebaseControllerRepository) : ControllerRepository {
        return repo
    }


    @Singleton
    @Provides
    fun providesPackageRepository(repo : AndroidPackageRepository) : PackageRepository {
        return repo
    }

    @Singleton
    @Provides
    fun providesUninstallRepository(repo : AndroidUninstallRepository) : UninstallRepository {
        return repo
    }
}