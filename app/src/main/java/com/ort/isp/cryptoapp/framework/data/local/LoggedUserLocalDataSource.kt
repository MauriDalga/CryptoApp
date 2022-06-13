package com.ort.isp.cryptoapp.framework.data.local

import android.app.Application
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.core.content.edit
import com.ort.isp.cryptoapp.data.model.LoggedUserLocalData
import com.ort.isp.cryptoapp.data.model.`in`.LoggedInUser
import com.ort.isp.cryptoapp.data.source.LocalSessionDataSource
import com.ort.isp.cryptoapp.framework.tools.QrGenerator
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


class LoggedUserLocalDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val application: Application,
    private val qrGenerator: QrGenerator
) :
    LocalSessionDataSource {

    override fun save(loggedInUser: LoggedInUser): LoggedUserLocalData {
        sharedPreferences.edit {
            this.putString(ID_KEY, loggedInUser.id)
            this.putString(NAME_KEY, loggedInUser.name)
            this.putString(LASTNAME_KEY, loggedInUser.lastname)
            this.putString(EMAIL_KEY, loggedInUser.email)
            this.putString(TOKEN_KEY, loggedInUser.token)
            this.putString(WALLET_ADDRESS_KEY, loggedInUser.walletAddress)
        }

        saveQrImage(loggedInUser.walletAddress)
        return get()!!
    }

    override fun get(): LoggedUserLocalData? {
        val id = sharedPreferences.getString(ID_KEY, null)
        val name = sharedPreferences.getString(NAME_KEY, null)
        val lastname = sharedPreferences.getString(LASTNAME_KEY, null)
        val email = sharedPreferences.getString(EMAIL_KEY, null)
        val sessionToken = sharedPreferences.getString(TOKEN_KEY, null)
        val walletAddress = sharedPreferences.getString(WALLET_ADDRESS_KEY, null)
        val cachedQrImageUri = sharedPreferences.getString(QR_IMAGE_URI_KEY, null)

        return if (id == null || name == null || lastname == null || email == null || sessionToken == null || walletAddress == null || cachedQrImageUri == null)
            null
        else
            LoggedUserLocalData(
                id,
                name,
                lastname,
                email,
                sessionToken,
                walletAddress,
                cachedQrImageUri
            )
    }

    override fun delete() {
        sharedPreferences.edit {
            this.clear()
        }
    }

    private fun saveQrImage(walletAddress: String) {
        val imagePath = File(application.filesDir, "my_images/")
        imagePath.mkdirs()
        val file = File(imagePath, "wallet_address_qr.png")
        val fileOutputStream: FileOutputStream
        try {
            val qrImage = qrGenerator.generateQrImageFrom(walletAddress)
            fileOutputStream = FileOutputStream(file)
            qrImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()

        } catch (e: Exception) {
            Log.e(TAG, "Failed to save QR image on cache storage", e)
            return
        }

        val myImageFileUri: Uri = FileProvider.getUriForFile(
            application,
            application.packageName.toString() + ".provider",
            file
        )

        sharedPreferences.edit { this.putString(QR_IMAGE_URI_KEY, myImageFileUri.toString()) }
    }
}

private const val ID_KEY = "ID_KEY"
private const val NAME_KEY = "NAME_KEY"
private const val LASTNAME_KEY = "LASTNAME_KEY"
private const val EMAIL_KEY = "EMAIL_KEY"
private const val TOKEN_KEY = "TOKEN_KEY"
private const val WALLET_ADDRESS_KEY = "WALLET_ADDRESS_KEY"
private const val QR_IMAGE_URI_KEY = "QR_IMAGE_URI_KEY"

private const val TAG = "LoggedUserLocalDataSource"

const val QR_IMAGE_PATH = "my_images/wallet_address_qr.png"
const val QR_IMAGE_TYPE = "image/png"