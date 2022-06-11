package com.ort.isp.cryptoapp.framework.ui.shared

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun Fragment.showMessage(msgString: String) {
    val appContext = context?.applicationContext ?: return
    Toast.makeText(appContext, msgString, Toast.LENGTH_LONG).show()
}