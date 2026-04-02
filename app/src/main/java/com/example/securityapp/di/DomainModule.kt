package com.example.securityapp.di

import com.example.securityapp.modules.messages.data.repository.AndroidMessageSerializer
import com.example.securityapp.modules.uninstall.data.UninstallRepoImpl
import com.example.securityapp.modules.app_settings.data.AppAppSettingsRepoImpl
import com.example.securityapp.modules.app_settings.domain.AppSettingsRepository
import com.example.securityapp.modules.connection.data.FirebaseConnectionRepository
import com.example.securityapp.modules.messages.data.repository.RoomMessagesRepository
import com.example.securityapp.modules.login.data.SecurityLoginRepository
import com.example.securityapp.modules.connection.domain.ConnectionRepository
import com.example.securityapp.modules.login.domain.LoginRepository
import com.example.securityapp.modules.messages.domain.repository.MessagesRepository
import com.example.securityapp.modules.messages.domain.repository.MessageSerializer
import com.example.securityapp.modules.uninstall.domain.UninstallRepository
import com.example.securityapp.modules.device_owner.data.AndroidDeviceOwnerRepository
import com.example.securityapp.modules.gallery.data.AndroidGalleryRepository
import com.example.securityapp.modules.controlled.data.repository.FirebaseControlledRepository
import com.example.securityapp.modules.location.data.FusedLocationRepository
import com.example.securityapp.modules.phone.domain.PhoneRepository
import com.example.securityapp.modules.overlay.domain.OverlayRepository
import com.example.securityapp.modules.phone.data.TelephoneRepository
import com.example.securityapp.modules.controlled.domain.repository.ControlledRepository
import com.example.securityapp.modules.device_owner.domain.DeviceOwnerRepository
import com.example.securityapp.modules.gallery.domain.GalleryRepository
import com.example.securityapp.modules.location.domain.LocationRepository
import com.example.securityapp.modules.overlay.data.OverlayControllerImpl
import com.example.securityapp.modules.controller.data.repository.FirebaseControllerRepository
import com.example.securityapp.modules.controller.domain.repository.ControllerRepository
import com.example.securityapp.modules.packages.data.AndroidPackageRepository
import com.example.securityapp.modules.packages.domain.PackageRepository
import com.example.securityapp.modules.permissions.data.PermissionRepoImpl
import com.example.securityapp.modules.permissions.domain.PermissionRepository
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
    fun providesSettingsRepository(repo : AppAppSettingsRepoImpl) : AppSettingsRepository {
        return repo
    }


    @Singleton
    @Provides
    fun providesSmsManagerRepository(repo : AndroidMessageSerializer) : MessageSerializer {
        return repo
    }

    @Singleton
    @Provides
    fun providesPermissionRepository(repo : PermissionRepoImpl) : PermissionRepository {
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
    fun providesUninstallRepository(repo : UninstallRepoImpl) : UninstallRepository {
        return repo
    }
}