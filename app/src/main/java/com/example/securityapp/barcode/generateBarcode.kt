package com.example.securityapp.barcode

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import androidx.core.graphics.set
import androidx.core.graphics.createBitmap

fun generateBarcode(data: String, width: Int = 1200, height: Int = 500): Bitmap{
    val clean = data.replace("-", "")
    val bitMatrix = MultiFormatWriter().encode(
        clean,
        BarcodeFormat.CODE_128,
        width,
        height
    )

    val bmp = createBitmap(width, height, Bitmap.Config.RGB_565)
    for (x in 0 until width) {
        for (y in 0 until height) {
            bmp[x, y] = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
        }
    }

    return bmp
}