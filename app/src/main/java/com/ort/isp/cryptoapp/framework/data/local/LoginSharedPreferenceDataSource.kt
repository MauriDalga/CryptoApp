package com.ort.isp.cryptoapp.framework.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ort.isp.cryptoapp.data.source.LocalSessionDataSource
import com.ort.isp.cryptoapp.framework.data.model.LoggedInUser
import javax.inject.Inject


class LoginSharedPreferenceDataSource @Inject constructor(private val sharedPreferences: SharedPreferences) :
    LocalSessionDataSource {

    override fun save(loggedInUser: LoggedInUser) {
        sharedPreferences.edit {
            this.putString(ID_KEY, loggedInUser.userId)
            this.putString(DISPLAY_NAME_KEY, loggedInUser.displayName)
            this.putString(TOKEN_KEY, loggedInUser.token)
        }
    }

    override fun get(): LoggedInUser? {
        val id = sharedPreferences.getString(ID_KEY, null)
        val displayName = sharedPreferences.getString(DISPLAY_NAME_KEY, null)
        val sessionToken = sharedPreferences.getString(TOKEN_KEY, null)

        return if (id == null || displayName == null || sessionToken == null) null else LoggedInUser(id, displayName, sessionToken)
    }

    override fun delete() {
        sharedPreferences.edit {
            this.clear()
        }
    }
}

private const val ID_KEY = "ID_KEY"
private const val DISPLAY_NAME_KEY = "DISPLAY_NAME_KEY"
private const val TOKEN_KEY = "TOKEN_KEY"