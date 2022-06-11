package com.ort.isp.cryptoapp.framework.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ort.isp.cryptoapp.data.model.`in`.LoggedInUser
import com.ort.isp.cryptoapp.data.source.LocalSessionDataSource
import javax.inject.Inject


class LoginSharedPreferenceDataSource @Inject constructor(private val sharedPreferences: SharedPreferences) :
    LocalSessionDataSource {

    override fun save(loggedInUser: LoggedInUser) {
        sharedPreferences.edit {
            this.putString(ID_KEY, loggedInUser.id)
            this.putString(NAME_KEY, loggedInUser.name)
            this.putString(LASTNAME_KEY, loggedInUser.lastname)
            this.putString(EMAIL_KEY, loggedInUser.email)
            this.putString(TOKEN_KEY, loggedInUser.token)
        }
    }

    override fun get(): LoggedInUser? {
        val id = sharedPreferences.getString(ID_KEY, null)
        val name = sharedPreferences.getString(NAME_KEY, null)
        val lastName = sharedPreferences.getString(LASTNAME_KEY, null)
        val email = sharedPreferences.getString(EMAIL_KEY, null)
        val sessionToken = sharedPreferences.getString(TOKEN_KEY, null)

        return if (id == null || name == null || lastName == null || email == null || sessionToken == null)
            null
        else
            LoggedInUser(id, name, lastName, email, sessionToken)
    }

    override fun delete() {
        sharedPreferences.edit {
            this.clear()
        }
    }
}

private const val ID_KEY = "ID_KEY"
private const val NAME_KEY = "NAME_KEY"
private const val LASTNAME_KEY = "LASTNAME_KEY"
private const val EMAIL_KEY = "EMAIL_KEY"
private const val TOKEN_KEY = "TOKEN_KEY"