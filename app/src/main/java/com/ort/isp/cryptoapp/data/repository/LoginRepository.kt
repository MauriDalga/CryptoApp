package com.ort.isp.cryptoapp.data.repository

import com.ort.isp.cryptoapp.data.model.Resource
import com.ort.isp.cryptoapp.data.model.`in`.LoggedInUser
import com.ort.isp.cryptoapp.data.model.out.LoginCredential
import com.ort.isp.cryptoapp.data.source.LocalSessionDataSource
import com.ort.isp.cryptoapp.data.source.RemoteLoginDataSource
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
class LoginRepository @Inject constructor(
    private val remoteLoginDataSource: RemoteLoginDataSource,
    private val localSessionDataSource: LocalSessionDataSource
) {

    // in-memory cache of the loggedInUser object
    private var user: LoggedInUser? = null

    private val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    fun getSession(): LoggedInUser? = if (isLoggedIn) user else localSessionDataSource.get()

    suspend fun login(credential: LoginCredential): Resource<LoggedInUser> {
        val result = remoteLoginDataSource.login(credential)

        if (result is Resource.Success) {
            setLoggedInUser(result.data!!)
        }

        return result
    }

    fun logout() {
        user = null
        localSessionDataSource.delete()
    }

    fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        localSessionDataSource.save(loggedInUser)
    }
}