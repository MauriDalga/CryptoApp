package com.ort.isp.cryptoapp.framework.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ort.isp.cryptoapp.databinding.ActivityLoggedUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoggedUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoggedUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoggedUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}