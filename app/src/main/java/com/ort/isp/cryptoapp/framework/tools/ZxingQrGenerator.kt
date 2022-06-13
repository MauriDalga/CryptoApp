package com.ort.isp.cryptoapp.framework.tools

import android.graphics.Bitmap
import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder


class ZxingQrGenerator : QrGenerator {
    override fun generateQrImageFrom(data: String): Bitmap {
        val writer = MultiFormatWriter()
        return try {
            val matrix = writer.encode(data, BarcodeFormat.QR_CODE, 800, 800)
            val encoder = BarcodeEncoder()
            encoder.createBitmap(matrix)
        } catch (e: WriterException) {
            Log.e(TAG, "Failed to create QR image", e)
            throw e
        }
    }
}

const val TAG = "QrGenerator"