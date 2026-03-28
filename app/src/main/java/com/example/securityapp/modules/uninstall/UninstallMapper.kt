package com.example.securityapp.modules.uninstall

import com.example.securityapp.modules.uninstall.UninstallEntity
import com.example.securityapp.modules.controlled.domain.models.UninstallDomain

fun UninstallDomain.uninstallDomainToUninstallEntity(): UninstallEntity {
    return UninstallEntity(packageName = packageName)
}
fun UninstallEntity.uninstallEntityToUninstallDomain(): UninstallDomain {
    return UninstallDomain(packageName = packageName)
}