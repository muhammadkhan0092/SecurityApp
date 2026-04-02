package com.example.securityapp.modules.uninstall.data

import com.example.securityapp.modules.uninstall.domain.UninstallDomainModel

fun UninstallDomainModel.uninstallDomainToUninstallEntity(): UninstallDataModel {
    return UninstallDataModel(packageName = packageName)
}
fun UninstallDataModel.uninstallEntityToUninstallDomain(): UninstallDomainModel {
    return UninstallDomainModel(packageName = packageName)
}