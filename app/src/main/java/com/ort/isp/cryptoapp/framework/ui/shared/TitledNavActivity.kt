package com.ort.isp.cryptoapp.framework.ui.shared

import androidx.appcompat.app.AppCompatActivity

abstract class TitledNavActivity : AppCompatActivity() {
    abstract fun setNavTitle(title: String)
}