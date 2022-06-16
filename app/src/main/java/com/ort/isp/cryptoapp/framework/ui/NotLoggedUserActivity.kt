package com.ort.isp.cryptoapp.framework.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ort.isp.cryptoapp.data.repository.LoginRepository
import com.ort.isp.cryptoapp.databinding.ActivityNotLoggedUserBinding
import com.ort.isp.cryptoapp.framework.data.local.NotificationTokenCache
import com.ort.isp.cryptoapp.framework.ui.shared.NEW_LOGIN_NEEDED
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotLoggedUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotLoggedUserBinding

    @Inject
    lateinit var loginRepository: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (intent.extras?.get(NEW_LOGIN_NEEDED) as Boolean?)?.let {
            if (it) loginRepository.logout()
        }

        if (loginRepository.getSession() != null) {
            startActivity(Intent(this, LoggedUserActivity::class.java))
            finish()
        }

        binding = ActivityNotLoggedUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.i(TAG, "Token generation success, TOKEN:${token}")
            NotificationTokenCache.firebaseToken = token
        })
    }
}

const val TAG = "NotLoggedUserActivity"