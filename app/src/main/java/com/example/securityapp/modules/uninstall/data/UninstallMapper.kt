package com.example.securityapp.modules.uninstall.data

import com.example.securityapp.modules.uninstall.domain.UninstallDomainModel

fun UninstallDomainModel.uninstallDomainToUninstallEntity(): UninstallEntity {
    return UninstallEntity(packageName = packageName)
}
fun UninstallEntity.uninstallEntityToUninstallDomain(): UninstallDomainModel {
    return UninstallDomainModel(packageName = packageName)
}