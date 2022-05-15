package com.ort.isp.cryptoapp.framework.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ort.isp.cryptoapp.data.repository.LoginRepository
import com.ort.isp.cryptoapp.databinding.ActivityNotLoggedUserBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotLoggedUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotLoggedUserBinding

    @Inject
    lateinit var loginRepository: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (loginRepository.getSession() != null) {
            startActivity(Intent(this, LoggedUserActivity::class.java))
            finish()
        }

        binding = ActivityNotLoggedUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}