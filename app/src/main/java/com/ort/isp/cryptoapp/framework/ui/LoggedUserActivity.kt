package com.ort.isp.cryptoapp.framework.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ort.isp.cryptoapp.R
import com.ort.isp.cryptoapp.data.repository.LoginRepository
import com.ort.isp.cryptoapp.databinding.ActivityLoggedUserBinding
import com.ort.isp.cryptoapp.framework.data.notification.TransactionNotificationService
import com.ort.isp.cryptoapp.framework.ui.shared.NEW_LOGIN_NEEDED
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

        val logoutButton = binding.logoutButton
        logoutButton.setOnClickListener {
            showLogoutDialog()
        }

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

        TransactionNotificationService.transactionReachedLiveData.observe(this) {
            if (it) {
                binding.newTransactionIcon.visibility = View.VISIBLE
            } else {
                binding.newTransactionIcon.visibility = View.INVISIBLE
            }
        }
        binding.newTransactionIcon.setOnClickListener {
            navView.selectedItemId = R.id.navigation_transaction_history
        }
    }

    override fun setNavTitle(title: String) {
        binding.simpleTitle.text = title
    }

    private fun getFriendlyName(name: String?, lastname: String?) = "$name ${lastname?.get(0)}."

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogCustom)
        builder.setMessage(getString(R.string.logout_dialog_message))
            .setCancelable(false)
            .setPositiveButton(
                getString(R.string.logout_dialog_yes_option)
            ) { _, _ ->
                val intent = Intent(this, NotLoggedUserActivity::class.java)
                intent.putExtra(NEW_LOGIN_NEEDED, true)
                startActivity(intent)
                this.finish()
            }
            .setNegativeButton(
                getString(R.string.logout_dialog_no_option)
            ) { _, _ ->
            }
        val alert: AlertDialog = builder.create()
        alert.setTitle(getString(R.string.logout_dialog_title))
        alert.show()
    }
}