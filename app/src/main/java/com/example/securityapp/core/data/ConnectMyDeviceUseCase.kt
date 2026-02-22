package com.example.securityapp.core.data

import com.example.securityapp.core.data.repository.DeviceRepository
import com.example.securityapp.firebase.DtoDevice
import com.example.securityapp.utils.Result

class ConnectMyDeviceUseCase(
    val deviceRepository: DeviceRepository
) {
    operator suspend fun invoke(barcode : String,deviceId : String){
        val device = deviceRepository.getDevice(barcode)
        when(device){
            is Result.Error -> TODO()
            is Result.Success->{
                val data = device.data
//                val newBlockedList = data.blockedParents.toMutableList()
//                newBlockedList.add(deviceId)
//                val newData = DtoDevice(barcodeId = data.barcodeId,data.phoneNumbers,newBlockedList)
            }
        }
        return
    }
}