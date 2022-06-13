package com.ort.isp.cryptoapp.framework.tools

import android.graphics.Bitmap

interface QrGenerator {
    fun generateQrImageFrom(data: String): Bitmap
}