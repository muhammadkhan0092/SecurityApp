package com.example.securityapp.modules.barcode

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException

fun generateBarcode(
    useQrCode: Boolean = false,
    width: Int = 900,
    height: Int = if (useQrCode) 900 else 350,
    data : String,
): Bitmap? {

    val format = if (useQrCode) BarcodeFormat.QR_CODE else BarcodeFormat.CODE_128

    val hints = mapOf(
        EncodeHintType.MARGIN to 10,
        EncodeHintType.CHARACTER_SET to "UTF-8"
    )

    return try {
        val bitMatrix = MultiFormatWriter().encode(
            data,
            format,
            width,
            height,
            hints
        )

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }

        bitmap
    } catch (e: WriterException) {
        e.printStackTrace()
        null
    }
}