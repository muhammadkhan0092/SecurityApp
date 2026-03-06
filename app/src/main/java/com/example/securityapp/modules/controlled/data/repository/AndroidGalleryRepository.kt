package com.example.securityapp.modules.controlled.data.repository

import android.os.Environment
import com.example.securityapp.modules.controlled.domain.repository.GalleryRepository
import java.io.File
import javax.inject.Inject

class AndroidGalleryRepository @Inject constructor() : GalleryRepository {
    override fun deleteAllGalleryFiles() {
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