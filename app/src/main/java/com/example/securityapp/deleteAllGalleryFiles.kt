package com.example.securityapp

import android.os.Environment
import android.util.Log
import java.io.File

fun deleteAllGalleryFiles() {
    Log.d("KHAN","IN DELETING ALL FILES")
    val dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
    val pictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

    deleteRecursive(dcim)
    deleteRecursive(pictures)
}

fun deleteRecursive(file: File?) {
    file?.let {
        if (it.isDirectory) {
            Log.d("KHAN","DELETING DIRECTORY ${it.name}")
            it.listFiles()?.forEach { child ->
                Log.d("KHAN","DELETING ${child.name}")
                deleteRecursive(child)
            }
        }
        it.delete()
    }
}
