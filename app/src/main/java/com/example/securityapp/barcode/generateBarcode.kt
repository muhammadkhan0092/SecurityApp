package com.example.securityapp.barcode

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException

//fun generateBarcode(
//    width: Int = 1200,
//    height: Int = 400
//): Bitmap {
//    val data = "rockzzz0092@gmail.com"
//    val hints = mapOf(
//        EncodeHintType.MARGIN to 16,
//        EncodeHintType.CHARACTER_SET to "UTF-8"
//    )
//    val bitMatrix = MultiFormatWriter().encode(
//        data,
//        BarcodeFormat.CODE_128,
//        width,
//        height,
//        hints
//    )
//    val bitmap = createBitmap(width, height, Bitmap.Config.ARGB_8888)
//    for (x in 0 until width) {
//        for (y in 0 until height) {
//            bitmap[x, y] = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
//        }
//    }
//    return bitmap
//}
fun generateBarcode(
    useQrCode: Boolean = false,
    width: Int = 900,
    height: Int = if (useQrCode) 900 else 350,
    data : String,
): Bitmap? {

    val format = if (useQrCode) BarcodeFormat.QR_CODE else BarcodeFormat.CODE_128

    val hints = mapOf(
        EncodeHintType.MARGIN to 10,           // Quiet zone for proper scanning
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