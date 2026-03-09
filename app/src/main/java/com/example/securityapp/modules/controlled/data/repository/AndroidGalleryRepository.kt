package com.example.securityapp.modules.controlled.data.repository

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.example.securityapp.modules.controlled.domain.repository.GalleryRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidGalleryRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : GalleryRepository {

    override fun deleteAllGalleryFiles() {

        val resolver = context.contentResolver

        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.MEDIA_TYPE
        )

        val selection =
            "${MediaStore.Files.FileColumns.MEDIA_TYPE}=? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE}=?"

        val selectionArgs = arrayOf(
            MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(),
            MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()
        )

        val uri = MediaStore.Files.getContentUri("external")

        resolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->

            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)

            while (cursor.moveToNext()) {

                val id = cursor.getLong(idColumn)

                val deleteUri = ContentUris.withAppendedId(uri, id)

                try {
                    resolver.delete(deleteUri, null, null)
                    Log.d("GalleryDelete", "Deleted media id: $id")
                } catch (e: Exception) {
                    Log.e("GalleryDelete", "Failed to delete id: $id")
                }
            }
        }
    }
}