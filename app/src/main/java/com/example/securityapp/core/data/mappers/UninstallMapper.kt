package com.example.securityapp.core.data.mappers

import com.example.securityapp.core.data.models.UninstallEntity
import com.example.securityapp.modules.controlled.domain.models.UninstallDomain

fun UninstallDomain.uninstallDomainToUninstallEntity(): UninstallEntity {
    return UninstallEntity(packageName = packageName)
}
fun UninstallEntity.uninstallEntityToUninstallDomain(): UninstallDomain {
    return UninstallDomain(packageName = packageName)
}