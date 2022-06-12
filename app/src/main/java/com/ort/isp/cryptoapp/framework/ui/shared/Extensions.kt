package com.ort.isp.cryptoapp.framework.ui.shared

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.ort.isp.cryptoapp.framework.ui.NotLoggedUserActivity
import java.io.ByteArrayOutputStream


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun Fragment.showMessage(msgString: String) {
    val appContext = context?.applicationContext ?: return
    Toast.makeText(appContext, msgString, Toast.LENGTH_LONG).show()
}

fun String.toBase64Bitmap(): Bitmap {
    val imageBytes = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

fun Fragment.logout() {
    val intent = Intent(context, NotLoggedUserActivity::class.java)
    intent.putExtra(NEW_LOGIN_NEEDED, true)
    startActivity(intent)
    activity?.finish()
}

fun Bitmap.toBase64String(): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}