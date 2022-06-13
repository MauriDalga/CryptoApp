package com.ort.isp.cryptoapp.framework.ui

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.data.repository.LoginRepository
import com.ort.isp.cryptoapp.databinding.ActivityLoggedUserBinding
import com.ort.isp.cryptoapp.framework.ui.shared.TitledNavActivity
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
        loginRepository.getSession()?.let {
            binding.personNameTitle.text = getFriendlyName(it.name, it.lastname)
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_logged_user) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_share_qr, R.id.navigation_home, R.id.navigation_transaction_history,
                R.id.navigation_transaction_flow, R.id.navigation_market_price
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun setNavTitle(title: String) {
        binding.simpleTitle.text = title
    }

    private fun getFriendlyName(name: String?, lastname: String?) = "$name ${lastname?.get(0)}."
}