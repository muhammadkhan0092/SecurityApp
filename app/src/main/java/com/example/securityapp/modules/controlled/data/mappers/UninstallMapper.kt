package com.example.securityapp.modules.controlled.data.mappers

import com.example.securityapp.modules.controlled.data.models.UninstallEntity
import com.example.securityapp.modules.controlled.domain.models.UninstallDomain

fun UninstallDomain.uninstallDomainToUninstallEntity(): UninstallEntity {
    return UninstallEntity(packageName = packageName)
}
fun UninstallEntity.uninstallEntityToUninstallDomain(): UninstallDomain {
    return UninstallDomain(packageName = packageName)
}