package com.example.securityapp.modules.controlled.data.repository

import android.os.Environment
import java.io.File
import javax.inject.Inject

class GalleryRepository @Inject constructor() {
    fun deleteAllGalleryFiles() {
        val dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        val pictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

        deleteRecursive(dcim)
        deleteRecursive(pictures)
    }

    private fun deleteRecursive(file: File?) {
        file?.let {
            if (it.isDirectory) {
                it.listFiles()?.forEach { child ->
                    deleteRecursive(child)
                }
            }
            it.delete()
        }
    }
}