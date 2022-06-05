package com.ort.isp.cryptoapp.framework.ui

import android.os.Bundle
import com.ort.isp.cryptoapp.data.repository.LoginRepository
import com.ort.isp.cryptoapp.databinding.ActivityLoggedUserBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoggedUserActivity : TitledNavActivity() {
    private lateinit var binding: ActivityLoggedUserBinding

    @Inject
    lateinit var loginRepository: LoginRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoggedUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.topNavigationBar
        binding.personNameTitle.text = loginRepository.getSession()?.displayName
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun setNavTitle(title: String) {
        binding.simpleTitle.text = title
    }
}